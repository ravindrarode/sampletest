package com.iserve.retrofitdemo.utility

object AppConfig {

    var mProductionEnable = true //False for UAT
    var mProURL = "http://pokka.srvlive01-iserve.com/api/"
    var mUatURL = "http://pokka.srvlive01-iserve.com/api/"
    val URL = if (mProductionEnable) mProURL else mUatURL
    var URL_PRODUCT_CATEGORY = "http://pokka.srvlive01-iserve.com/api/category/all"
}