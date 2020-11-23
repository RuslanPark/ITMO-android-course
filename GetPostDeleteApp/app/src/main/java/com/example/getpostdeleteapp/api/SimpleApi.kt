package com.example.getpostdeleteapp.api

import com.example.getpostdeleteapp.model.Post
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    @GET("posts")
    suspend fun getPosts(
        @Query("_sort") sort : String,
        @Query("_order") order : String
    ) : Response<List<Post>>

    @DELETE("posts/{postNumber}")
    suspend fun deletePost(
        @Path("postNumber") number : Int
    ) : Response<ResponseBody>

    @POST("posts")
    suspend fun pushPost(
        @Body post : Post
    ) : Response<Post>
}