package com.example.getpostdeleteapp.repository

import android.content.Context
import com.example.getpostdeleteapp.module.NetworkConnectionInterceptor
import com.example.getpostdeleteapp.module.SimpleApiModule
import com.example.getpostdeleteapp.model.Post
import okhttp3.ResponseBody
import retrofit2.Response

class Repository (context: Context) {

    init {
        SimpleApiModule.httpClient.addInterceptor(NetworkConnectionInterceptor(context))
    }

    suspend fun getPosts(sort: String, order: String) : Response<List<Post>> {
        return SimpleApiModule.api.getPosts(sort, order)
    }

    suspend fun deletePost(number: Int) : Response<ResponseBody> {
        return SimpleApiModule.api.deletePost(number)
    }

    suspend fun pushPost(post: Post) : Response<Post> {
        return SimpleApiModule.api.pushPost(post)
    }
}