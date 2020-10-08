package com.example.photoviewerapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_photo.*
import java.lang.ref.WeakReference
import java.net.URL

class PhotoActivity : AppCompatActivity() {

    private class PhotoLoader(activity: PhotoActivity) : AsyncTask<String, Void, Bitmap>() {

        private val activityRef = WeakReference(activity)

        override fun doInBackground(vararg params: String): Bitmap? {
            val imageUrl = params[0]
            var bimage: Bitmap? = null
            try {
                val inputStream = URL(imageUrl).openStream()
                bimage = BitmapFactory.decodeStream(inputStream)

            } catch (e: Exception) {
                Log.e("image", "Failed to load image ${e.message}", e)
                e.printStackTrace()
            }
            return bimage
        }

        override fun onPostExecute(result: Bitmap) {
            val activity = activityRef.get()
            activity?.showPhoto(result)
        }
    }

    internal fun showPhoto(result: Bitmap?) {
        image_view.setImageBitmap(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        //Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show()
        PhotoLoader(this).execute(intent.extras?.getString("url"))
    }

}