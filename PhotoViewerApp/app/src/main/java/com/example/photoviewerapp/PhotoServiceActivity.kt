package com.example.photoviewerapp

import android.app.IntentService
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


class PhotoServiceActivity : IntentService("PhotoService") {

    val ACTION_FIN = "FIN"
    var photoAbsolutePath = ""

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
        photoAbsolutePath = saveToInternalStorage(bimage)
    }

    override fun onDestroy() {
        super.onDestroy()
        val sentIntent = Intent(ACTION_FIN)
        sentIntent.putExtra("PATH", photoAbsolutePath)
        sendBroadcast(sentIntent)
    }
    private fun saveToInternalStorage(bitmapImage: Bitmap?) : String {
        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageDir", MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.getAbsolutePath()
    }
}