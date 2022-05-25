package com.example.photosdemo.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photosdemo.databinding.ItemPhotoBinding

class PhotoAdapter(private val context: Context) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    inner class PhotoHolder(binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(context), parent, false)
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {

    }

    override fun getItemCount(): Int = 10
}