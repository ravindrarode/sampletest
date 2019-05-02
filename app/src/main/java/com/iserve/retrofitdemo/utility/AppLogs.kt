package com.iserve.retrofitdemo.utility

import android.util.Log

object AppLogs {
    private val mFlag = true

    fun d(paramString1: String, paramString2: String) {
        if (mFlag) {
            Log.d(paramString1, paramString2)
        }
    }

    fun e(paramString1: String, paramString2: String) {
        if (mFlag) {
            Log.e(paramString1, paramString2)
        }
    }

    fun i(paramString1: String, paramString2: String) {
        if (mFlag) {
            Log.i(paramString1, paramString2)
        }
    }

    fun v(paramString1: String, paramString2: String) {
        if (mFlag) {
            Log.v(paramString1, paramString2)
        }
    }
}