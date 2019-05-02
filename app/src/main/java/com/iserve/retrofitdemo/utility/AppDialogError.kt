package com.iserve.retrofitdemo.utility

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.iserve.retrofitdemo.R

object AppDialogError {
    private var appDialog: AppDialogError? = null

    val dialog: AppDialogError
        get() {
            if (appDialog == null) {
                appDialog = AppDialogError
            }
            return appDialog!!
        }

    fun showAlertDialog(paramString: String, paramContext: Context) {

        val dialog = Dialog(paramContext)
        dialog.requestWindowFeature(1)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.fragment_dialog_error)
        (dialog.findViewById(R.id.txt_page_sub_title) as TextView).text = paramString
        (dialog.findViewById(R.id.btn_accept) as Button).setOnClickListener { dialog.dismiss() }
        (dialog.findViewById(R.id.img_close)as ImageView).setOnClickListener(OnClickListener { dialog.dismiss() })
        dialog.show()
    }
}