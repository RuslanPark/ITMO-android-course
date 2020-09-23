package com.example.calculatorapp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun buttonClicked (view: View) {
        val buttonChar = (view as Button).text
        if (textViewResult.text.length == 1 && textViewResult.text[0].equals('0') && buttonChar[0].isDigit()) {
            textViewResult.text = buttonChar
        } else {
            textViewResult.append(buttonChar)
        }
    }

    fun evaluate (view: View) {
        try {
            textViewResult.text = ExpressionBuilder(textViewResult.text.toString()).build().evaluate().toString()
        } catch (e : ArithmeticException) {
            textViewResult.text = "Division by zero!"
        } catch (e : NumberFormatException) {
            textViewResult.text = "Incorrect number format!"
        } catch (e : IllegalArgumentException) {
            textViewResult.text = "Invalid expression!"
        } catch (e : Exception) {
            textViewResult.text = "Error!"
        }
    }

    fun cancelOne (view: View) {
        if (textViewResult.text.length > 1) {
            textViewResult.text = textViewResult.text.subSequence(0, textViewResult.text.length - 1)
        } else {
            textViewResult.text = "0"
        }
    }

    fun clearAll (view : View) {
        textViewResult.text = "0"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("result", textViewResult.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textViewResult.text = savedInstanceState.getString("result")
    }
}