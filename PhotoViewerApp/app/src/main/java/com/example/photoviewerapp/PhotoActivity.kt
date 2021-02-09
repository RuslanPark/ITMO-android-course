package com.example.photoviewerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.photoviewerapp.databinding.ActivityPhotoBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding
    private var retainedFragment: RetainedFragment? = null
    private var lruCache = LruCache<String, Bitmap>(1024 * 1024 * 48)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val fm : FragmentManager = supportFragmentManager
        retainedFragment = fm.findFragmentByTag("retain") as? RetainedFragment

        if (retainedFragment != null) {
            Log.i("retain", "has retain fragment")
            lruCache = retainedFragment!!.retain

            val imageUrl = intent.extras?.getString("url")

            val bimg = lruCache.get(imageUrl)
            if (bimg != null) {
                binding.imageView.setImageBitmap(lruCache.get(imageUrl))
            }
        } else {

            Log.i("retain","hasn't retain fragment")
            retainedFragment = RetainedFragment()
            fm.beginTransaction().add(retainedFragment!!, "retain").commit()


            val photoService = Intent(this, PhotoServiceActivity::class.java)
            photoService.putExtra("url", intent.extras?.getString("url"))
            startService(photoService)

            val progressReceiver = ProgressReceiver(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction("FIN")
            registerReceiver(progressReceiver, intentFilter)
        }
    }

    override fun onStop() {
        super.onStop()
        retainedFragment!!.retain = lruCache
    }

    private fun loadImageFromStorage(path: String?) {
        try {
            val f = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            binding.imageView.setImageBitmap(b)
            val imageUrl = intent.extras?.getString("url")
            lruCache.put(imageUrl, b)

            //f.delete()
            //File("path").delete()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    class ProgressReceiver(private val activity: PhotoActivity) : BroadcastReceiver() {
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
            retainInstance = true
        }
    }
}