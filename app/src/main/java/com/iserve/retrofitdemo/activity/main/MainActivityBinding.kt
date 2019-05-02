package com.iserve.retrofitdemo.activity.main

import android.databinding.BindingAdapter
import android.widget.TextView



    @BindingAdapter("setNewText")
    fun setName(txtView: TextView, url: String) {
        txtView.text=url
    }

