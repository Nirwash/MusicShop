package com.example.musicshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.musicshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding
    var quantity = 0
    val items = arrayOf("Guitar", "Keyboard", "Ukulele", "Drums")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.bPlus.setOnClickListener {
            b.tvCount.text = increaseQuantity().toString()
        }
        b.bMinus.setOnClickListener {
            b.tvCount.text = decreaseQuantity().toString()
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        b.spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@MainActivity, "Selected ${items[position]}", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
    private fun increaseQuantity(): Int {
        quantity++
        return quantity
    }private fun decreaseQuantity(): Int {
        if(quantity > 0) quantity--
        return quantity
    }


}