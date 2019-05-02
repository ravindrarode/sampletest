package com.iserve.retrofitdemo.net

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iserve.retrofitdemo.utility.AppConfig

import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val TIMEOUT = 600
    private var retrofit: Retrofit? = null
    private var retrofit1: Retrofit? = null
    private val retrofit2: Retrofit? = null

    val apiClient: Retrofit?
        get() {
            if (retrofit == null) {

                val localOkHttpClient = OkHttpClient.Builder().connectTimeout(100L, TimeUnit.SECONDS).readTimeout(100L, TimeUnit.SECONDS).build()
                val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
                retrofit = Retrofit.Builder().baseUrl(AppConfig.URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(localOkHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build()
            }
            return retrofit
        }

    val apiClient1: Retrofit?
        get() {
            if (retrofit1 == null) {

                retrofit1 = Retrofit.Builder()
                        .baseUrl(AppConfig.URL)
                        .addConverterFactory(StringConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit1
        }

    class StringConverterFactory : Converter.Factory() {

        override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
            return if (String::class.java == type) {
                Converter<ResponseBody, String> { value -> value.string() }
            } else null
        }

        override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
            return if (String::class.java == type) {
                Converter<String, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
            } else null

        }

        companion object {
            private val MEDIA_TYPE = MediaType.parse("text/plain")

            fun create(): StringConverterFactory {
                return StringConverterFactory()
            }
        }
    }
}