package com.example.photosdemo.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photosdemo.R
import com.example.photosdemo.app.DataState
import com.example.photosdemo.app.Dependences
import com.example.photosdemo.databinding.MainFragmentBinding
import com.example.photosdemo.network.Photo

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private var page = 0
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var photoAdapter: PhotoAdapter
    private var list = ArrayList<Photo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = Dependences.getViewModel(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnList()
        paging()
        with(binding) {
            photoAdapter = PhotoAdapter(requireContext())
            rvTeams.adapter = photoAdapter

            btnRetry.setOnClickListener {
                observeOnList()
                hideOrDisplayLoading(isLoading = true)
            }
        }
    }

    private fun isLastItemDisplayed(): Boolean {

        with(binding) {
            if (rvTeams.adapter?.itemCount != 0) {
                val lastItemPosition =
                    (rvTeams.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
                if (lastItemPosition != RecyclerView.NO_POSITION && lastItemPosition == rvTeams.adapter?.itemCount?.minus(
                        1
                    )
                )
                    return true
            }
            return false
        }
    }

    fun getListFromPage(photos: List<Photo>, page: Int): List<Photo> {
        val newList: MutableList<Photo> = java.util.ArrayList<Photo>()
        val index = if (photos.size > page * 10) page * 10 else photos.size
        for (i in 0 until index) {
            newList.add(photos[i])
        }
        return newList
    }

    private fun paging() {
        with(binding) {
            rvTeams.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (isLastItemDisplayed()) {
                        layLoading.isVisible = true
                        Handler().postDelayed({
                            layLoading.isVisible = false
                            photoAdapter.setPhotosList(
                                getListFromPage(
                                    list,
                                    page
                                ) as ArrayList<Photo>
                            )
                            photoAdapter.notifyDataSetChanged()
                            recyclerView.scrollToPosition((page - 1) * 10)
                            page++
                        }, 1000)
                    }
                }
            })

        }
    }


    private fun observeOnList() {
        viewModel.refreshMain(isFirstTime = true).observe(viewLifecycleOwner) {
            when (it.getStatus()) {

                DataState.DataStatus.LOADING -> {
                    hideOrDisplayLoading(isLoading = true)
                }
                DataState.DataStatus.SUCCESS -> {
                    hideOrDisplayLoading()
                    it?.getData()?.let { list ->
                        if (list.isNotEmpty()) {
                            page++
                            this.list = list as ArrayList<Photo>
                            photoAdapter.setPhotosList(
                                list = getListFromPage(
                                    this.list,
                                    page
                                ) as ArrayList<Photo>
                            )
                            photoAdapter.notifyDataSetChanged()
                        } else
                            hideOrDisplayLoading(hasError = true, error = getString(R.string.empty))

                    } ?: kotlin.run {
                        hideOrDisplayLoading(hasError = true, error = getString(R.string.empty))
                    }
                }
                DataState.DataStatus.ERROR -> {
                    hideOrDisplayLoading(hasError = true, error = it.getError()?.message.toString())

                }
                DataState.DataStatus.NO_INTERNET -> {
                    hideOrDisplayLoading(hasError = true, error = getString(R.string.noInternet))
                }
            }
        }
    }

    private fun hideOrDisplayLoading(
        isLoading: Boolean = false,
        hasError: Boolean = false,
        error: String = ""
    ) {
        with(binding) {
            pbProgressbar.isVisible = isLoading
            errorLayout.isVisible = hasError
            tvError.text = error
            rvTeams.isVisible = !(isLoading || hasError)
        }
    }
}