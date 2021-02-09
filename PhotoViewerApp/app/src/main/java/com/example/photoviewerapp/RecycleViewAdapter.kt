package com.example.contactlistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoviewerapp.Photo
import com.example.photoviewerapp.databinding.ItemBinding

class RecycleViewAdapter (val photos : ArrayList<Photo>, val onClick : (Photo) -> Unit) :
    RecyclerView.Adapter<RecycleViewAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(val binding : ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo : Photo) {
            with(binding) {
                imageDescription.text = photo.description
                imageUrl.text = photo.url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.PhotoViewHolder {
        val holder = PhotoViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        holder.binding.root.setOnClickListener {
            onClick(photos[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.bind(photos[position])

    override fun getItemCount() = photos.size

}