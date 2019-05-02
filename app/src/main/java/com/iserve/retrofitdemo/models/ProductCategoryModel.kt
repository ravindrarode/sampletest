package com.iserve.retrofitdemo.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class ProductCategoryModel : Serializable {

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("image")
    lateinit var image: String

    @SerializedName("status_id")
    lateinit var status_id: String

    @SerializedName("created_at")
    lateinit var created_at: String

    @SerializedName("updated_at")
    lateinit var updated_at: String

    @SerializedName("image_path")
    lateinit var image_path: String
}
