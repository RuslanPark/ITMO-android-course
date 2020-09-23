package com.example.myfirstapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun toastMe (view: View) {
        val message = textView.text
        val myToast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.BOTTOM, 0, 0)
        myToast.show()
    }

    fun countMe (view: View) {
        val counter =   textView.text.toString()
        val count = Integer.parseInt(counter) + 1
        textView.text = count.toString()
    }

    fun randomMe (view: View) {
        val rndIntent = Intent(this, RandomNumberActivity::class.java)

        val counter = textView.text.toString()
        val count = Integer.parseInt(counter)
        rndIntent.putExtra(RandomNumberActivity.TOTAL_COUNT, count)

        startActivity(rndIntent)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            Toast.makeText(this, "Focus", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("textViewNumber", textView.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView.text = savedInstanceState.getString("textViewNumber")
    }

}