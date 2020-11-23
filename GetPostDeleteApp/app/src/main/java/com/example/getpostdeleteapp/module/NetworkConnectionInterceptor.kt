package com.example.getpostdeleteapp.module

import android.content.Context
import android.net.ConnectivityManager
import com.example.getpostdeleteapp.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private var mContext: Context = context.applicationContext

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        if (!isConnected()) {
            throw NoConnectivityException()
        }
        //val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(chain.request())
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }

}