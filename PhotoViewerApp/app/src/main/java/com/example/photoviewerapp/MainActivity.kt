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

    private var listOfPhotos = arrayListOf<Photo>()

    private class DescriptionLoader(activity : MainActivity) : AsyncTask<Void, Void, ArrayList<Photo>>() {

        private val activityReference = WeakReference(activity)

        override fun doInBackground(vararg p0: Void?) : ArrayList<Photo> {
            val listOfPhotos = arrayListOf<Photo>()

            val url = "https://api.vk.com/method/photos.search?q=Nature&access_token=${BuildConfig.TOKEN}&v=5.124&count=100"
            var stringOnJSON = "Hello"
            try {
                stringOnJSON = InputStreamReader( URL(url).openConnection().getInputStream() ).readText()
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

        override fun onPostExecute(result: ArrayList<Photo>) {
            activityReference.get()?.showListOfDescriptions(result)
        }

    }

    internal fun showListOfDescriptions(result: ArrayList<Photo>) {
        listOfPhotos = result
        val viewManager = LinearLayoutManager(this)
        val photoAdapter = RecycleViewAdapter(result) {
            val intent = Intent(this, PhotoActivity::class.java)
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
        if (listOfPhotos.isEmpty()) {
            //Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show()
            DescriptionLoader(this).execute()
        } else {
            showListOfDescriptions(listOfPhotos)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("ListOfPhotos", listOfPhotos)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        listOfPhotos = savedInstanceState.getParcelableArrayList("ListOfPhotos")!!
    }
}