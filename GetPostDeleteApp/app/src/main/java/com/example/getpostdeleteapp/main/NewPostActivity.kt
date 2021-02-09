package com.example.getpostdeleteapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.getpostdeleteapp.databinding.ActivityNewPostBinding
import com.example.getpostdeleteapp.model.Post
import java.lang.Exception

class NewPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun send(view : View) {
        val intent = Intent()
        try {
            intent.putExtra(
                "post",
                Post(
                    Integer.parseInt(binding.editTextUserId.text.toString()),
                    Integer.parseInt(binding.editTextId.text.toString()),
                    binding.editTextTitle.text.toString(),
                    binding.editTextBody.text.toString()
                )
            )
        } catch (e : Exception) {
            Log.d("postExtra", "NumFormatException")
        }
        setResult(RESULT_OK, intent)
        finish()
    }

}