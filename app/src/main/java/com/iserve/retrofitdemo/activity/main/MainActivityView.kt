package com.iserve.retrofitdemo.activity.main

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Button
import com.iserve.retrofitdemo.R
import com.iserve.retrofitdemo.utility.AppConfig
import android.widget.TextView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.fabric.sdk.android.BuildConfig
import android.widget.Toast
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.iserve.retrofitdemo.databinding.ActivityMainViewBinding
import com.iserve.retrofitdemo.models.ProductCategoryModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivityView : Activity() {

    var mainActivityPresenter: MainActivityPresenter? = null
    var txtTitle: TextView? = null
    var edtEmailId:EditText?=null
    var edtPassword:EditText?=null
    var txtInputEmail: TextInputLayout?=null
    var txtInputPassword: TextInputLayout?=null
    var btnNextPage: Button?=null
    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
    var mValue:String?=null
    var binding: ActivityMainViewBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main_view)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_view)
        binding!!.setName("Rode")
        mainActivityPresenter=MainActivityPresenter();
        initView()
    }

    fun initView(){

        txtTitle=findViewById(R.id.txt_title) as TextView
        edtEmailId=findViewById(R.id.edt_email_id) as EditText
        edtPassword=findViewById(R.id.edt_password) as EditText
        btnNextPage=findViewById(R.id.btn_next_page) as Button
        txtInputEmail=findViewById(R.id.txt_input_email) as TextInputLayout
        txtInputPassword=findViewById(R.id.txt_input_password) as TextInputLayout

        val refreshedToken = FirebaseInstanceId.getInstance().getToken()

        //Log.e("token_key",refreshedToken)
        mFirebaseRemoteConfig= FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        mFirebaseRemoteConfig.setConfigSettings(configSettings)

        mValue= mFirebaseRemoteConfig.getString("image_view_enable")

        txtTitle!!.text=mValue

        mainActivityPresenter?.callFirstWebservice(this,AppConfig.URL_PRODUCT_CATEGORY,binding)
        //fetchWelcome()
        fetchWelcome()

        /*RxTextView.afterTextChangeEvents(edtEmailId!!)
                .skipInitialValue()
                .map {
                    txtInputEmail!!.error = null
                    it.view().text.toString()
                }
                .debounce(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(verifyEmailPattern)
                .compose(retryWhenError {
                    txtInputEmail!!.error = it.message
                })
                .subscribe()

        RxTextView.afterTextChangeEvents(edtPassword!!)
                .skipInitialValue()
                .map {
                    txtInputPassword!!.error = null
                    it.view().text.toString()
                }
                .debounce(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(retryWhenError {
                    txtInputPassword!!.error = it.message
                })
                .subscribe()*/

        btnNextPage!!.setOnClickListener {executeSimpleOperator()}
    }

    private fun executeSimpleOperator() {
        //progress.visibility = View.VISIBLE
        getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver())
    }

    private fun getObservable(): Observable<String> {
        return Observable.just("RxJava", "RxAndroid")
    }

    private fun getObserver(): Observer<String> {
        return object : Observer<String> {

            override fun onSubscribe(d: Disposable) {
                Log.d("TAG", " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: String) {
                edtEmailId!!.append(" onNext : value : " + value)
                edtEmailId!!.append("\n")
                Log.d("TAG", " onNext : value : " + value)
            }

            override fun onError(e: Throwable) {
                edtEmailId!!.append(" onError : " + e.message)
                edtEmailId!!.append("\n")
                Log.d("TAG", " onError : " + e.message)
            }

            override fun onComplete() {
                edtEmailId!!.append(" onComplete")
                edtEmailId!!.append("\n\n")
                Log.d("TAG", " onComplete")
            }
        }
    }

    private val lengthGreaterThanSix = ObservableTransformer<String, String> { observable ->
        observable.flatMap {
            Observable.just(it).map { it.trim() } // - abcdefg - |
                    .filter { it.length > 6 }
                    .singleOrError()
                    .onErrorResumeNext {
                        if (it is NoSuchElementException) {
                            Single.error(Exception("Length must be greater than 6"))
                        } else {
                            Single.error(it)
                        }
                    }
                    .toObservable()
        }
    }

    private val verifyEmailPattern = ObservableTransformer<String, String> { observable ->
        observable.flatMap {
            Observable.just(it).map { it.trim() }
                    .filter {
                        Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    }
                    .singleOrError()
                    .onErrorResumeNext {
                        if (it is NoSuchElementException) {
                            Single.error(Exception("Email not valid"))
                        } else {
                            Single.error(it)
                        }
                    }
                    .toObservable()
        }
    }

    private inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit): ObservableTransformer<String, String> = ObservableTransformer { observable ->
        observable.retryWhen { errors ->
            errors.flatMap {
                onError(it)
                Observable.just("")
            }

        }
    }


    private fun fetchWelcome() {
        mValue= mFirebaseRemoteConfig.getString("image_view_enable")

        txtTitle!!.text=mValue

        var cacheExpiration: Long = 3600 // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, object : OnCompleteListener<Void> {
                    override fun onComplete(task: Task<Void>) {
                        if (task.isSuccessful()) {
                            Toast.makeText(this@MainActivityView, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show()

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched()
                        } else {
                            Toast.makeText(this@MainActivityView, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show()
                        }
                        displayWelcomeMessage()
                    }
                })
        // [END fetch_config_with_callback]
    }

    private fun displayWelcomeMessage() {
        // [START get_config_values]
        val welcomeMessage = mFirebaseRemoteConfig.getString("image_view_enable")
        // [END get_config_values]
        txtTitle!!.text=welcomeMessage
    }

}
