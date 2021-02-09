package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    fun buttonClicked (view: View) {
        val buttonChar = (view as Button).text
        if (binding.textViewResult.text.length == 1 && binding.textViewResult.text[0].equals('0') && buttonChar[0].isDigit()) {
            binding.textViewResult.text = buttonChar
        } else {
            binding.textViewResult.append(buttonChar)
        }
    }

    fun evaluate (view: View) {
        try {
            binding.textViewResult.text = ExpressionBuilder(binding.textViewResult.text.toString()).build().evaluate().toString()
        } catch (e : ArithmeticException) {
            binding.textViewResult.text = getString(R.string.division_by_zero)
        } catch (e : NumberFormatException) {
            binding.textViewResult.text = getString(R.string.incorrect_number_format)
        } catch (e : IllegalArgumentException) {
            binding.textViewResult.text = getString(R.string.invalid_expression)
        } catch (e : Exception) {
            binding.textViewResult.text = getString(R.string.error)
        }
    }

    fun cancelOne (view: View) {
        if (binding.textViewResult.text.length > 1) {
            binding.textViewResult.text = binding.textViewResult.text.subSequence(0, binding.textViewResult.text.length - 1)
        } else {
            binding.textViewResult.text = "0"
        }
    }

    fun clearAll (view : View) {
        binding.textViewResult.text = "0"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("result", binding.textViewResult.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.textViewResult.text = savedInstanceState.getString("result")
    }
}