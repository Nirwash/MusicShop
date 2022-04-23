package com.example.musicshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.example.musicshop.data.Order
import com.example.musicshop.data.SharedViewModel
import com.example.musicshop.databinding.FragmentMainBinding

const val TAG = "MainFragmentTag"
class MainFragment: BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    private val b get() = _binding!!

    var quantity = 0
    val items = arrayOf("Guitar", "Keyboard", "Ukulele", "Drums")
    val goodsMap = mapOf("Guitar" to 750, "Keyboard" to 1000, "Ukulele" to 500, "Drums" to 1500)
    lateinit var goodsName: String
    var price = 0
    var image = R.drawable.guitar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_out_right)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        b.bPlus.setOnClickListener {
            b.tvCount.text = increaseQuantity().toString()
        }
        b.bMinus.setOnClickListener {
            b.tvCount.text = decreaseQuantity().toString()
        }
        b.bAddToCard.setOnClickListener {
            val name = b.edText.text.toString()
            val order = Order(name, goodsName, quantity, (price*quantity))
            model.setData(order)
            val fragment = OrderFragment()
            fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
        }

        createSpinner()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createSpinner() {
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
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