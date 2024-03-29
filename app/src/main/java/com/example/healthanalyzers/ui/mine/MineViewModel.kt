package com.example.healthanalyzers.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MineViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This  is me Fragment"
    }
    val text: LiveData<String> = _text

    // 用户名
    private val _username = MutableLiveData<String>().apply {
        value = "This  is me Fragment"
    }
    val username: LiveData<String> = _username
}