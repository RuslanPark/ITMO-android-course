package com.example.photoviewerapp

import android.app.IntentService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.net.URL


class PhotoServiceActivity : IntentService("PhotoService") {

    val INTENTSERVICE_ACTION = "PhotoService.RESPONSE"
    val EXTRA_KEY_OUT = "PHOTO"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        val imageUrl = intent?.getStringExtra("url")
        var bimage: Bitmap? = null
        try {
            val inputStream = URL(imageUrl).openStream()
            bimage = BitmapFactory.decodeStream(inputStream)

        } catch (e: Exception) {
            Log.e("image", "Failed to load image ${e.message}", e)
            e.printStackTrace()
        }

        val responseIntent = Intent()
        responseIntent.action = INTENTSERVICE_ACTION
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
        responseIntent.putExtra(EXTRA_KEY_OUT, bimage)
        sendBroadcast(responseIntent)
    }

}