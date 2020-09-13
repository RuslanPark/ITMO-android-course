package com.example.myfirstapp

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

        val myToastContainer = myToast.view as LinearLayout
        val iconImage = ImageView(this)
        iconImage.setImageResource(R.mipmap.ic_launcher)
        myToastContainer.addView(iconImage, 0)
        myToastContainer.setBackgroundColor(Color.TRANSPARENT)
        myToast.show()
    }

    fun countMe (view: View) {
        val counter =   textView.text.toString()
        val count = Integer.parseInt(counter) + 1
        textView.text = count.toString()
    }

    fun randomNumber (view: View) {
        textView.text = (0..10).random().toString()
    }

}