package com.example.musicshop

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.example.musicshop.data.Order
import com.example.musicshop.data.SharedViewModel
import com.example.musicshop.databinding.FragmentOrderBinding

class OrderFragment: BaseFragment() {
    private var _binding: FragmentOrderBinding? = null
    private val b get() = _binding!!
    private val email = arrayOf("Nirwash@bk.ru")
    private val subjectEmail = "Order from music shop"
    lateinit var emailText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
        enterTransition = inflater.inflateTransition(R.transition.slide_out_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        b.bBackMain.setOnClickListener {
            fm.popBackStack()
        }
        val order: Order = model.getData().value!!
        emailText = "Customer: ${order.customerName}\n" +
                "Goods: ${order.goodsName}\n" +
                "Quantity: ${order.quantity}\n" +
                "Price: ${order.orderPrice}$\n" +
                "Total: ${order.quantity * order.orderPrice}$"
        b.tvCustomerName.text = order.customerName
        b.tvGoodsName.text = order.goodsName
        b.tvQuantityCount.text = order.quantity.toString()
        b.tvPriceCount.text = order.orderPrice.toString()
        b.tvOrderPriceCount.text = (order.quantity * order.orderPrice).toString()
        b.btnSubmitOrder.setOnClickListener { composeEmail(email, subjectEmail, emailText) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun composeEmail(addresses: Array<String>, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        if (context?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
    }
}