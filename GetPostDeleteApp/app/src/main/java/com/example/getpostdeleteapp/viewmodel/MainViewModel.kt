package com.example.getpostdeleteapp.viewmodel

import android.util.AndroidRuntimeException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getpostdeleteapp.exceptions.NoConnectivityException
import com.example.getpostdeleteapp.model.Post
import com.example.getpostdeleteapp.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

class MainViewModel(private val repository : Repository) : ViewModel() {

    val internetConnection : MutableLiveData<Boolean> = MutableLiveData()
    val getResponse : MutableLiveData<Response<List<Post>>> = MutableLiveData()
    val deleteResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val pushResponse : MutableLiveData<Response<Post>> = MutableLiveData()

    fun getPosts(sort : String, order : String) {
        viewModelScope.launch {
            try {
                val response = repository.getPosts(sort, order)
                getResponse.value = response
                internetConnection.value = false
            } catch (e : NoConnectivityException) {
                internetConnection.value = true
            }
        }
    }

    fun deletePost(number : Int) {
        viewModelScope.launch {
            try {
                val response = repository.deletePost(number)
                deleteResponse.value = response
                internetConnection.value = false
            } catch (e : NoConnectivityException) {
                internetConnection.value = true
            }
        }
    }

    fun pushPost(post : Post) {
        viewModelScope.launch {
            try {
                val response = repository.pushPost(post)
                pushResponse.value = response
                internetConnection.value = false
            } catch (e : NoConnectivityException) {
                internetConnection.value = true
            }
        }
    }

}