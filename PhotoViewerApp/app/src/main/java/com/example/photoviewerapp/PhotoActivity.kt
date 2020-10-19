package com.example.photoviewerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.LruCache
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class PhotoActivity : AppCompatActivity() {


    private var retainedFragment: RetainedFragment? = null
    private var lruCache = LruCache<String, Bitmap>(1024 * 1024 * 48)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
    }

    override fun onResume() {
        super.onResume()

        System.out.println("onResume")

        val fm : FragmentManager = getSupportFragmentManager()
        retainedFragment = fm.findFragmentByTag("retain") as? RetainedFragment

        if (retainedFragment != null) {
            System.out.println("!=NULL")
            lruCache = retainedFragment!!.retain

            val imageUrl = intent.extras?.getString("url")

            val bimg = lruCache.get(imageUrl)
            if (bimg != null) {
                image_view.setImageBitmap(lruCache.get(imageUrl))
            }
        } else {

            System.out.println("==NULL")
            retainedFragment = RetainedFragment()
            fm.beginTransaction().add(retainedFragment!!, "retain").commit()

            //System.out.println("Resume")
            val photoService = Intent(this, PhotoServiceActivity::class.java)
            photoService.putExtra("url", intent.extras?.getString("url"))
            startService(photoService)

            val progressReceiver = ProgressReceiver(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction("FIN")
            registerReceiver(progressReceiver, intentFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        retainedFragment!!.retain = lruCache
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
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    class ProgressReceiver(val activity: PhotoActivity) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intentReq: Intent?) {
            if (intentReq?.action.equals("FIN")) {
                activity.loadImageFromStorage(intentReq?.extras?.getString("PATH"))
            }
        }
    }

    class RetainedFragment : Fragment() {
        // data object we want to retain
        var retain = LruCache<String, Bitmap>(1024 * 1024 * 48)

        // this method is only called once for this fragment
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // retain this fragment
            setRetainInstance(true)
        }
    }

    //"/data/data/com.example.photoviewerapp/app_imageDir"
}