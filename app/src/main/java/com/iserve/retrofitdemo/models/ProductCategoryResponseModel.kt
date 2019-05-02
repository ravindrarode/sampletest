package com.iserve.retrofitdemo.response

import com.google.gson.annotations.SerializedName
import com.iserve.retrofitdemo.models.ProductCategoryModel

import java.io.Serializable
import java.util.ArrayList



class ProductCategoryResponseModel : Serializable {

    @SerializedName("data")
    lateinit var data: ArrayList<ProductCategoryModel>
}
