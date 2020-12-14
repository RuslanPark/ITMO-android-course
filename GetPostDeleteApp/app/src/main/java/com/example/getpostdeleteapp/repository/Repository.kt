package com.example.getpostdeleteapp.repository

import android.content.Context
import com.example.getpostdeleteapp.module.NetworkConnectionInterceptor
import com.example.getpostdeleteapp.module.SimpleApiModule
import com.example.getpostdeleteapp.model.Post
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class Repository (context: Context) {

    init {
        SimpleApiModule.httpClient.addInterceptor(NetworkConnectionInterceptor(context))
    }

    fun getPosts(sort: String, order: String) : Observable<Response<List<Post>>> {
        return SimpleApiModule.api.getPosts(sort, order)
    }

    fun deletePost(number: Int) : Observable<Response<ResponseBody>> {
        return SimpleApiModule.api.deletePost(number)
    }

    fun pushPost(post: Post) : Observable<Response<Post>> {
        return SimpleApiModule.api.pushPost(post)
    }
}