package com.example.photoviewerapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlistapp.RecycleViewAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var listOfPhotos : List<Photo>? = null

    private class DescriptionLoader(activity : MainActivity) : AsyncTask<Void, Void, List<Photo>>() {

        private val activityReference = WeakReference(activity)

        override fun doInBackground(vararg p0: Void?) : List<Photo> {
            val listOfPhotos = mutableListOf<Photo>()

            val url = "https://api.vk.com/method/photos.search?q=Book&access_token=${BuildConfig.TOKEN}&v=5.124&count=100"
            var stringOnJSON = "Hello"
            try {
                stringOnJSON = InputStreamReader( URL(url).openConnection().getInputStream() ).use { it.readText() }
            } catch (e : Exception) {
                e.printStackTrace()
            }

            val builder = GsonBuilder()
            val gson = builder.create()
            val response : Response? = gson.fromJson(stringOnJSON, Response::class.java)
            for (i in response?.response?.items!!) {
                listOfPhotos.add(Photo(i.text, i.sizes.last().url))
            }

            return listOfPhotos
        }

        override fun onPostExecute(result: List<Photo>) {
            activityReference.get()?.onLoadCompleted(result)
        }

    }

    internal fun onLoadCompleted(result: List<Photo>) {
        listOfPhotos = result
        val viewManager = LinearLayoutManager(this@MainActivity)
        val photoAdapter = RecycleViewAdapter(result) {
            val intent = Intent(this@MainActivity, PhotoActivity::class.java)
            intent.putExtra("url", it.url)
            startActivity(intent)
        }
        if (photoAdapter.itemCount == 0) {
            Toast.makeText(this, "No results", Toast.LENGTH_SHORT).show()
        }
        recycler_view.apply {
            layoutManager = viewManager
            adapter = photoAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (listOfPhotos == null) {
            DescriptionLoader(this).execute()
        }
    }
}