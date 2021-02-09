package com.example.getpostdeleteapp.api

import com.example.getpostdeleteapp.model.Post
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    @GET("posts")
    fun getPosts(
        @Query("_sort") sort : String,
        @Query("_order") order : String
    ) : Observable<Response<List<Post>>>

    @DELETE("posts/{postNumber}")
    fun deletePost(
        @Path("postNumber") number : Int
    ) : Observable<Response<ResponseBody>>

    @POST("posts")
    fun pushPost(
        @Body post : Post
    ) : Observable<Response<Post>>
}