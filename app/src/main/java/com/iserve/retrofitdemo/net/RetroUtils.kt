package com.iserve.retrofitdemo.net

import android.content.Context
import com.google.gson.Gson
import com.iserve.retrofitdemo.utility.AppLogs
import java.net.SocketTimeoutException
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import rx.Observable
import rx.Subscription

object RetroUtils {
    private val TAG = RetroUtils::class.java.simpleName
    private val isShow = true

    fun handleServiceFailure(paramThrowable: Throwable, paramContext: Context) {
        try {
            val bool = paramThrowable.cause is SocketTimeoutException
            if (bool) {
                return
            }

        } catch (e: Exception) {

        }

    }

    fun printErrorMsg(paramString1: String, paramString2: String, paramInt: Int) {
        var paramString1 = paramString1
        if (isShow) {
            paramString1 = "$paramString1 : Error Msg : $paramString2 : Error Code : $paramInt"
            AppLogs.e(TAG, paramString1)
        }
    }

    fun printException(paramString: String, paramException: Exception) {
        var paramString = paramString
        if (isShow) {
            paramString = paramString + " : Error Msg : " + paramException.message
            AppLogs.e(TAG, paramString)
        }
    }

    fun printReqDetails(paramString: String, paramCall: Call<*>?, paramObject: Any?) {
        var paramString = paramString
        if (isShow && paramCall != null) {
            var str1 = ""
            val str2 = paramCall.request().url().toString()
            val str3 = paramCall.request().method()
            if (paramCall.request() != null) {
                str1 = paramCall.request().body()!!.contentType()!!.toString()
            }
            val bool = paramCall.request().isHttps
            paramString = "$paramString : URL : $str2 \nMethod : $str3 \tContentType : $str1 \tisHttps : $bool"
            AppLogs.i(TAG, paramString)
            if (paramObject != null) {

                try {
                    paramString = Gson().toJson(paramObject)
                    AppLogs.i(TAG, paramString)
                    return
                } catch (e: Exception) {
                    AppLogs.e(TAG, "Exception to parse json object")
                    e.printStackTrace()
                }

            }
        }

    }

    fun printReqUrl(paramString1: String, paramString2: String, paramString3: String) {
        var paramString1 = paramString1
        if (isShow) {
            paramString1 = "$paramString1 : URL : $paramString2 \nBody : $paramString3"
            AppLogs.i(TAG, paramString1)
        }
    }

    fun printResDetails(paramObject: Any?) {
        var paramObject = paramObject
        if (paramObject != null) {
            try {
                paramObject = Gson().toJson(paramObject)
                AppLogs.i(TAG, paramObject)
                return
            } catch (e: Exception) {
                AppLogs.e(TAG, "Exception to parse json object")
                e.printStackTrace()
            }

        }

    }

    fun printResDetails(paramObject: Any?, paramCall: Observable<*>) {
        var paramObject = paramObject
        AppLogs.v(TAG, paramCall.toString())
        if (paramObject != null) {
        }
        try {
            paramObject = Gson().toJson(paramObject)
            AppLogs.i(TAG, paramObject)
            return
        } catch (e: Exception) {
            AppLogs.e(TAG, "Exception to parse json object")
            e.printStackTrace()
        }

    }
}