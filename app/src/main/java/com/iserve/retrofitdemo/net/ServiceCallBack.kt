package com.iserve.retrofitdemo.net

interface ServiceCallBack {

    val API_PRODUCT_CATEGORY: Int
        get() = 1

    abstract fun onRequestComplete(paramObject: Any, paramInt: Int)

    abstract fun onRequestCancel(paramString: String, paramInt: Int)

    abstract fun onError(paramString: String, paramInt: Int)
}