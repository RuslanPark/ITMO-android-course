package com.example.getpostdeleteapp.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getpostdeleteapp.adapter.RecycleViewAdapter
import com.example.getpostdeleteapp.databinding.ActivityMainBinding
import com.example.getpostdeleteapp.model.Post
import com.example.getpostdeleteapp.repository.Repository
import com.example.getpostdeleteapp.viewmodel.MainViewModel
import com.example.getpostdeleteapp.viewmodel.MainViewModelFactory
import com.example.getpostdeleteapp.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var listOfPosts = mutableListOf<Post>()
    private lateinit var viewModel: MainViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var postAdapter: RecycleViewAdapter

    private fun postsGet() {
        binding.progressBar.visibility = ProgressBar.VISIBLE
        viewModel.getPosts("id", "asc")
    }

    private fun postDelete(post: Post) {
        binding.progressBar.visibility = ProgressBar.VISIBLE
        viewModel.deletePost(post.id)
        postViewModel.deleteData(post)
    }

    private fun postPush(post: Post) {
        binding.progressBar.visibility = ProgressBar.VISIBLE
        viewModel.pushPost(post)
        postViewModel.insertData(post)
    }

    private fun showListOfPosts() {
        binding.progressBar.visibility = ProgressBar.INVISIBLE

        val viewManager = LinearLayoutManager(this)
        postAdapter = RecycleViewAdapter(listOfPosts) {
            postDelete(it)
        }
        if (postAdapter.itemCount == 0) {
            Toast.makeText(this, "No saved data, please refresh", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.apply {
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
            if (it.isEmpty() || !this::postAdapter.isInitialized) {
                showListOfPosts()
            } else {
                postAdapter.update(listOfPosts)
            }
            binding.progressBar.visibility = ProgressBar.INVISIBLE
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = ProgressBar.VISIBLE

        val repository = Repository(this)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        bindObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val post: Post? = data?.getParcelableExtra("post")
        if (post == null) {
            Toast.makeText(this, "Post data format is incorrect", Toast.LENGTH_SHORT).show()
        } else {
            postPush(post)
            binding.progressBar.visibility = ProgressBar.VISIBLE
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