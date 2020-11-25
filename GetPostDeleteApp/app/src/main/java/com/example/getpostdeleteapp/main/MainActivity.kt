package com.example.getpostdeleteapp.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getpostdeleteapp.R
import com.example.getpostdeleteapp.adapter.RecycleViewAdapter
import com.example.getpostdeleteapp.model.Post
import com.example.getpostdeleteapp.repository.Repository
import com.example.getpostdeleteapp.viewmodel.MainViewModel
import com.example.getpostdeleteapp.viewmodel.MainViewModelFactory
import com.example.getpostdeleteapp.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listOfPosts = mutableListOf<Post>()
    private lateinit var viewModel: MainViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var postAdapter: RecycleViewAdapter

    private fun postsGet() {
        viewModel.getPosts("id", "asc")
    }

    private fun postDelete(post: Post) {
        viewModel.deletePost(post.id)
        postViewModel.deleteData(post)
    }

    private fun postPush(post: Post) {
        viewModel.pushPost(post)
        postViewModel.insertData(post)
    }

    private fun showListOfPosts() {
        progressBar.visibility = ProgressBar.INVISIBLE

        val viewManager = LinearLayoutManager(this)
        postAdapter = RecycleViewAdapter(listOfPosts) {
            postDelete(it)
        }
        recyclerView.apply {
            layoutManager = viewManager
            adapter = postAdapter
        }
    }

    private fun bindObservers() {
        viewModel.deleteResponse.observe(this, { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Delete is successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Post does not exist", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.pushResponse.observe(this, { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "New post pushed successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Post did not pushed", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getResponse.observe(this, { response ->
            if (response.isSuccessful) {
                postViewModel.clearData()
                postViewModel.insertAllData(response.body() as MutableList<Post>)
            } else {
                Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.internetConnection.observe(this, {
            if (it) {
                val myDialogFragment = DialogFragment()
                val manager = supportFragmentManager
                myDialogFragment.show(manager, "myDialog")
                //Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        })

        postViewModel.readAllData.observe(this, {
            listOfPosts = it
            postAdapter.update(listOfPosts)
            progressBar.visibility = ProgressBar.INVISIBLE
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = ProgressBar.VISIBLE

        val repository = Repository(this)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        bindObservers()
        showListOfPosts()
    }

    override fun onResume() {
        super.onResume()

        if (listOfPosts.isEmpty()) {
            postsGet()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val post: Post? = data?.getParcelableExtra("post")
        if (post == null) {
            Toast.makeText(this, "Post data format is incorrect", Toast.LENGTH_SHORT).show()
        } else {
            postPush(post)
            progressBar.visibility = ProgressBar.VISIBLE
        }
    }

    fun newPost(view: View) {
        val intent = Intent(this, NewPostActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun refreshPosts(view: View) {
        postsGet()
    }

}