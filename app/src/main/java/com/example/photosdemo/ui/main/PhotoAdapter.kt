package com.example.photosdemo.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.photosdemo.databinding.ItemPhotoBinding
import com.example.photosdemo.network.Photo

class PhotoAdapter(private val context: Context) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    private var list = ArrayList<Photo>()

    fun setPhotosList(list: ArrayList<Photo>) {
        this.list = list
    }

    inner class PhotoHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {

            with(binding) {
                val image = GlideUrl(
                    photo.url, LazyHeaders.Builder().addHeader("User-Agent", "5")
                        .build()
                )
                Glide.with(context).load(image)
                    .error(androidx.appcompat.R.drawable.abc_action_bar_item_background_material)
                    .placeholder(com.google.android.material.R.drawable.notify_panel_notification_icon_bg)
                    .centerCrop()
                    .into(ivPhoto)

                tvTitle.text = photo.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(context), parent, false)
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}