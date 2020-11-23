package com.example.getpostdeleteapp.repository

import androidx.lifecycle.LiveData
import com.example.getpostdeleteapp.data.PostDao
import com.example.getpostdeleteapp.model.Post

class PostRepository(private val postDao : PostDao) {

    suspend fun insertAllData(posts : MutableList<Post>) = postDao.insertAllData(posts)

    suspend fun insertData(post : Post) = postDao.insertData(post)

    val findAllData : LiveData<MutableList<Post>> = postDao.findAllData()

    suspend fun clearData() = postDao.clearData()

    suspend fun deleteData(post : Post) = postDao.deleteData(post)

}