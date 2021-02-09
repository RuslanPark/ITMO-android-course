package com.example.getpostdeleteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getpostdeleteapp.databinding.ItemBinding
import com.example.getpostdeleteapp.model.Post

class RecycleViewAdapter (private var posts : MutableList<Post>, val onClickDeleteButton : (Post) -> Unit) :
    RecyclerView.Adapter<RecycleViewAdapter.PostViewHolder>() {

    class PostViewHolder(val binding : ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post : Post) {
            with(binding) {
                val titleStr = post.userId.toString() + " " + post.id.toString() + " " + post.title
                postTitle.text = titleStr
                postBody.text = post.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        holder.binding.deleteButton.setOnClickListener {
            val post = posts[holder.adapterPosition]
            onClickDeleteButton(post)
        }
        return holder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(posts[position])

    override fun getItemCount() = posts.size

    fun update(listOfPosts : MutableList<Post>) {
        posts = listOfPosts
        notifyDataSetChanged()
    }

}