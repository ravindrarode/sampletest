package com.iserve.retrofitdemo.apimanager

import android.content.Context
import com.iserve.retrofitdemo.net.ApiClient
import com.iserve.retrofitdemo.net.ApiInterface
import com.iserve.retrofitdemo.net.RetroUtils
import com.iserve.retrofitdemo.net.ServiceCallBack
import com.iserve.retrofitdemo.response.ProductCategoryResponseModel
import com.iserve.retrofitdemo.utility.AppLogs
import com.iserve.retrofitdemo.utility.AppProgressBar
import java.util.HashMap

import okhttp3.Headers
import okhttp3.Headers.Builder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

class UserManager {
    private val TAG = "UserManager"

    //Call API for Fetch All Categories
    fun callProductCategoryAPI(paramContext: Context, paramHashMap: String, paramServiceCallBack: ServiceCallBack, paramInt: Int) {

        AppProgressBar.instance.showProgress(paramContext)
        val observable = (ApiClient.apiClient!!.create(ApiInterface::class.java) as ApiInterface).callProductCategory(paramHashMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        RetroUtils.printResDetails(paramHashMap, observable)
        observable.subscribe(object : Observer<ProductCategoryResponseModel> {

            internal lateinit var productCategoryResponseModel: ProductCategoryResponseModel
            override fun onCompleted() {
                RetroUtils.printResDetails(productCategoryResponseModel)
                AppProgressBar.instance.cancelProgress()
                paramServiceCallBack.onRequestComplete(productCategoryResponseModel, paramInt)
            }

            override fun onError(e: Throwable) {
                AppLogs.e("UserManager", e.toString())
                AppProgressBar.instance.cancelProgress()
                paramServiceCallBack.onRequestCancel(e.message!!, paramInt)
            }

            override fun onNext(productCategoryResponseModel: ProductCategoryResponseModel) {
                this.productCategoryResponseModel = productCategoryResponseModel
            }
        })
    }

    companion object {
        private var manger: UserManager? = null

        val instants: UserManager
            get() {
                if (manger == null) {
                    manger = UserManager()
                }
                return manger as UserManager
            }
    }
}