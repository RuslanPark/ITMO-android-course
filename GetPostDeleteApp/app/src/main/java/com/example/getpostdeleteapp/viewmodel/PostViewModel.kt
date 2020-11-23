package com.example.getpostdeleteapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.getpostdeleteapp.data.PostDao
import com.example.getpostdeleteapp.data.PostDatabase
import com.example.getpostdeleteapp.model.Post
import com.example.getpostdeleteapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    var readAllData : LiveData<MutableList<Post>>
    private val repository  : PostRepository

    init {
        val postDao = PostDatabase.getDatabase(application).postDao()
        repository = PostRepository(postDao)
        readAllData = repository.findAllData
    }

    fun insertData(post : Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(post)
            readAllData = repository.findAllData
        }
    }

    fun insertAllData(posts : MutableList<Post>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllData(posts)
            readAllData = repository.findAllData
        }
    }

    fun clearData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearData()
            readAllData = repository.findAllData
        }
    }

    fun deleteData(post : Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(post)
            readAllData = repository.findAllData
        }
    }


}