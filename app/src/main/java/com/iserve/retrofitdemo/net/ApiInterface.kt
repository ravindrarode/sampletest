package com.iserve.retrofitdemo.net


import com.iserve.retrofitdemo.response.ProductCategoryResponseModel
import retrofit2.http.GET
import retrofit2.http.Url
import rx.Observable

interface ApiInterface {

    @GET
    abstract fun callProductCategory(@Url paramString: String): Observable<ProductCategoryResponseModel>
}