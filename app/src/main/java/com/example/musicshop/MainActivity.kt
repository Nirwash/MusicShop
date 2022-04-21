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
    val goodsMap = mapOf("Guitar" to 750, "Keyboard" to 1000, "Ukulele" to 500, "Drums" to 1500)
    lateinit var goodsName: String
    var price = 0
    var image = R.drawable.guitar


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

        createSpinner()
    }

    private fun createSpinner() {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    getPrice()
                    setItemImage(goodsName)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    getPrice()
                }
            }
        }
    }

    private fun increaseQuantity(): Int {
        quantity++
        getPrice()
        return quantity
    }

    private fun decreaseQuantity(): Int {
        if (quantity > 0) quantity--
        getPrice()
        return quantity
    }

    private fun getPrice() {
        goodsName = b.spinner.selectedItem.toString()
        price = goodsMap[goodsName]!!
        b.tvCurrentPrice.text = (quantity * price).toString()
    }

    private fun setItemImage(goodsName: String) {
        image = when (goodsName) {
            "Keyboard" -> R.drawable.piano
            "Ukulele" -> R.drawable.ukulele
            "Drums" -> R.drawable.drums
            else -> R.drawable.guitar
        }
        b.imgItem.setImageResource(image)
    }


}