package com.example.photosdemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.photosdemo.app.DataState
import com.example.photosdemo.app.Dependences
import com.example.photosdemo.databinding.MainFragmentBinding
import com.example.photosdemo.network.Photo

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var photoAdapter: PhotoAdapter

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
        with(binding) {
            photoAdapter = PhotoAdapter(requireContext())
            rvTeams.adapter = photoAdapter
        }
    }


    private fun observeOnList() {
        viewModel.refreshMain().observe(viewLifecycleOwner) {
            when (it.getStatus()) {

                DataState.DataStatus.LOADING -> {
                    hideOrDisplayLoading(isLoading = true)
                }
                DataState.DataStatus.SUCCESS -> {
                    hideOrDisplayLoading()
                    it?.getData()?.let { list ->
                        if (list.isNotEmpty())
                            photoAdapter.setPhotosList(list = list as ArrayList<Photo>)
                        else
                            hideOrDisplayLoading(hasError = true, error = "Empty List")

                    } ?: kotlin.run {
                        hideOrDisplayLoading(hasError = true, error = "Empty List")
                    }
                }
                DataState.DataStatus.ERROR -> {
                    hideOrDisplayLoading(hasError = true, error = it.getError()?.message.toString())

                }
                DataState.DataStatus.NO_INTERNET -> {
                    hideOrDisplayLoading(hasError = true, error = it.getError()?.message.toString())
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
            tvError.isVisible = hasError
            tvError.text = error
            rvTeams.isVisible = !(isLoading || hasError)
        }
    }
}