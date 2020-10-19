package com.example.photoviewerapp

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class PhotoActivity : AppCompatActivity() {


    private val lruCache = LruCache<String, Bitmap>(1024 * 1024 * 48)

    /*private class PhotoLoader(activity: PhotoActivity) : AsyncTask<String, Void, Bitmap>() {

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
        //image_view.setImageBitmap(result)
        loadImageFromStorage(saveToInternalStorage(result!!)!!)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        //Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show()
        //PhotoLoader(this).execute(intent.extras?.getString("url"))
        val imageUrl = intent.extras?.getString("url")
        System.out.println(imageUrl)
        val bimg = lruCache.get(imageUrl)
        if (bimg != null) {
            image_view.setImageBitmap(lruCache.get(imageUrl))
        }
    }

    override fun onResume() {
        super.onResume()

        System.out.println("Resume")
        val photoService = Intent(this, PhotoServiceActivity::class.java)
        photoService.putExtra("url", intent.extras?.getString("url"))
        startService(photoService)

        //val bimage = BitmapFactory.decodeStream(FileInputStream("image_d.png"))
        //image_view.setImageBitmap(bimage)
        val progressReceiver = ProgressReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction("PROGRESS")
        intentFilter.addAction("FIN")
        registerReceiver(progressReceiver, intentFilter)
        //loadImageFromStorage()
        //image_view.setImageResource()

        /*val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memoryClass = am.memoryClass
        System.out.println(memoryClass)
        var cacheSize = 1024 * 1024 * 384 / 8*/
    }

    private fun loadImageFromStorage(path: String?) {
        try {
            val f = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            image_view.setImageBitmap(b)
            val imageUrl = intent.extras?.getString("url")
            lruCache.put(imageUrl, b)

            f.delete()
            File("path").delete()
            //System.out.println("Image set")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    class ProgressReceiver(val activity: PhotoActivity) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intentReq: Intent?) {
            if (intentReq?.action.equals("FIN")) {
                //System.out.println(intentReq?.extras?.getString("PATH"))
                activity.loadImageFromStorage(intentReq?.extras?.getString("PATH"))
            }
        }
    }

    //"/data/data/com.example.photoviewerapp/app_imageDir"
}