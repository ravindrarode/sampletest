package com.iserve.retrofitdemo.activity.main

import android.app.Activity
import com.iserve.retrofitdemo.apimanager.UserManager
import com.iserve.retrofitdemo.databinding.ActivityMainViewBinding
import com.iserve.retrofitdemo.net.ConnectionChecker
import com.iserve.retrofitdemo.net.RetroUtils
import com.iserve.retrofitdemo.net.ServiceCallBack
import com.iserve.retrofitdemo.response.ProductCategoryResponseModel
import com.iserve.retrofitdemo.utility.AppDialogError
import com.iserve.retrofitdemo.utility.AppLogs


class MainActivityPresenter : ServiceCallBack{

    lateinit var activity: Activity
    var binding: ActivityMainViewBinding?=null

    fun callFirstWebservice(activity: Activity, url: String, binding: ActivityMainViewBinding?){

        this.activity=activity
        this.binding=binding
        if (ConnectionChecker.instance.isConnected(activity)){
            UserManager.instants.callProductCategoryAPI(activity,url,this,API_PRODUCT_CATEGORY)
        }else{
            AppDialogError.dialog.showAlertDialog("No Internet Connection",activity);
        }
    }

    override fun onRequestComplete(paramObject: Any, paramInt: Int) {
        RetroUtils.printResDetails(paramObject)

        when (paramInt) {
            API_PRODUCT_CATEGORY -> {
                val productCategoryResponseModel = paramObject as ProductCategoryResponseModel
                val productCategoryModels = productCategoryResponseModel.data
                binding!!.setProductCategoryModel(productCategoryModels.get(1))
                AppLogs.e("xyz",productCategoryModels.get(1).name)
            }
        }
    }

    override fun onRequestCancel(paramString: String, paramInt: Int) {
        AppDialogError.dialog.showAlertDialog(paramString,activity)
    }

    override fun onError(paramString: String, paramInt: Int) {
        AppDialogError.dialog.showAlertDialog(paramString,activity);
    }


}