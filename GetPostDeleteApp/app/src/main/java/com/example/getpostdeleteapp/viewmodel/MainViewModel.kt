package com.example.getpostdeleteapp.viewmodel

import android.annotation.SuppressLint
import android.util.AndroidRuntimeException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getpostdeleteapp.exceptions.NoConnectivityException
import com.example.getpostdeleteapp.model.Post
import com.example.getpostdeleteapp.repository.Repository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository) : ViewModel() {

    val internetConnection: MutableLiveData<Boolean> = MutableLiveData()
    val getResponse: MutableLiveData<Response<List<Post>>> = MutableLiveData()
    val deleteResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val pushResponse: MutableLiveData<Response<Post>> = MutableLiveData()

    fun getPosts(sort: String, order: String) {
        val disposable =
            repository.getPosts(sort, order).subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getResponse.value = it
                }, {
                    if (it.javaClass.simpleName == "NoConnectivityException") {
                        internetConnection.value = true
                    } else {
                        throw it
                    }
                }, {
                    internetConnection.value = false
                })
    }

    fun deletePost(number: Int) {
        val disposable =
            repository.deletePost(number).subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteResponse.value = it
                }, {
                    if (it.javaClass.simpleName == "NoConnectivityException") {
                        internetConnection.value = true
                    } else {
                        throw it
                    }
                }, {
                    internetConnection.value = false
                })
    }

    fun pushPost(post: Post) {
        val disposable =
            repository.pushPost(post).subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    pushResponse.value = it
                }, {
                    if (it.javaClass.simpleName == "NoConnectivityException") {
                        internetConnection.value = true
                    } else {
                        throw it
                    }
                }, {
                    internetConnection.value = false
                })
    }
}