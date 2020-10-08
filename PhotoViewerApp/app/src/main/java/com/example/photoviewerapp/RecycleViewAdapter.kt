package com.example.contactlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoviewerapp.Photo
import com.example.photoviewerapp.R
import kotlinx.android.synthetic.main.item.view.*

class RecycleViewAdapter (val photos : ArrayList<Photo>, val onClick : (Photo) -> Unit) :
    RecyclerView.Adapter<RecycleViewAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(val root : View) : RecyclerView.ViewHolder(root) {
        fun bind(photo : Photo) {
            with(root) {
                image_description.text = photo.description
                image_url.text = photo.url
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.PhotoViewHolder {
        val holder = PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        holder.root.setOnClickListener {
            onClick(photos[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.bind(photos[position])

    override fun getItemCount() = photos.size

}