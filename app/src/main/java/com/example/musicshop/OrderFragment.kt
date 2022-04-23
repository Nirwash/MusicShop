package com.example.musicshop

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
    ): View? {
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
        b.tvOrder.text = order.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}