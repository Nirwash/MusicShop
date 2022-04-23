package com.example.musicshop.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val mutableLiveData: MutableLiveData<Order> = MutableLiveData()

    fun setData(order: Order) {
        mutableLiveData.value = order
    }

    fun getData(): MutableLiveData<Order> = mutableLiveData

}