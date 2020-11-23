package com.example.getpostdeleteapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.getpostdeleteapp.R
import com.example.getpostdeleteapp.model.Post
import kotlinx.android.synthetic.main.activity_new_post.*
import java.lang.Exception

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
    }

    fun send(view : View) {
        val intent = Intent()
        try {
            intent.putExtra(
                "post",
                Post(
                    Integer.parseInt(editTextUserId.text.toString()),
                    Integer.parseInt(editTextId.text.toString()),
                    editTextTitle.text.toString(),
                    editTextBody.text.toString()
                )
            )
        } catch (e : Exception) {
            Log.d("postExtra", "NumFormatException")
        }
        setResult(RESULT_OK, intent)
        finish()
    }

}