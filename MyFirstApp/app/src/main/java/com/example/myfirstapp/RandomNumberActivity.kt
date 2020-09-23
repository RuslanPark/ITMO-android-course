package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_random_number.*
import kotlin.random.Random

class RandomNumberActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_COUNT = "total_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_number)
        showRandomNumber()
    }

    fun showRandomNumber() {
        val count = intent.getIntExtra(TOTAL_COUNT, 0)

        var randomNumber = 0
        if (count > 0) {
            randomNumber = Random.nextInt(count + 1)
        }

        textViewRndNumber.text = Integer.toString(randomNumber)
        textViewRndHeader.text = getString(R.string.random_heading, count)
    }

    fun shutDown(view: View) {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("textViewRndNumber", textViewRndNumber.text.toString())
        outState.putString("textViewRndHeader", textViewRndHeader.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textViewRndNumber.text = savedInstanceState.getString("textViewRndNumber")
        textViewRndHeader.text = savedInstanceState.getString("textViewRndHeader")
    }

}