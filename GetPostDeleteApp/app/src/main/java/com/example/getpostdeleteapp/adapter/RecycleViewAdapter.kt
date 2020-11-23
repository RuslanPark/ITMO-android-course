package com.example.getpostdeleteapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getpostdeleteapp.R
import com.example.getpostdeleteapp.model.Post
import kotlinx.android.synthetic.main.item.view.*

class RecycleViewAdapter (private var posts : MutableList<Post>, val onClickDeleteButton : (Post) -> Unit) :
    RecyclerView.Adapter<RecycleViewAdapter.PostViewHolder>() {

    class PostViewHolder(val root : View) : RecyclerView.ViewHolder(root) {
        fun bind(post : Post) {
            with(root) {
                val titleStr = post.userId.toString() + " " + post.id.toString() + " " + post.title
                postTitle.text = titleStr
                postBody.text = post.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        holder.root.deleteButton.setOnClickListener {
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