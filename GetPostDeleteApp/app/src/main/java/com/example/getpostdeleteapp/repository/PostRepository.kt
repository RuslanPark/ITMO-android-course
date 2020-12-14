package com.example.getpostdeleteapp.repository

import com.example.getpostdeleteapp.data.PostDao
import com.example.getpostdeleteapp.model.Post

class PostRepository(private val postDao : PostDao) {

    fun insertAllData(posts : MutableList<Post>) = postDao.insertAllData(posts)

    fun insertData(post : Post) = postDao.insertData(post)

    fun findAllData() : MutableList<Post> = postDao.findAllData()

    fun clearData() = postDao.clearData()

    fun deleteData(post : Post) = postDao.deleteData(post)

}