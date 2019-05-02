package com.iserve.retrofitdemo.utility

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.support.v7.app.AlertDialog.Builder
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar

import com.github.ybq.android.spinkit.style.DoubleBounce
import com.iserve.retrofitdemo.R


class AppProgressBar {


    fun showProgress(paramContext: Context) {

        dialogBuilder = AlertDialog.Builder(paramContext)
        val layout_inflater = (paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.layout_loader, null)
        dialogBuilder.setView(layout_inflater)
        //mProgressBar = (ImageView)layout_inflater.findViewById(R.id.main_progress);
        progressBar = layout_inflater.findViewById(R.id.spin_kit)
        val doubleBounce = DoubleBounce()
        progressBar!!.indeterminateDrawable = doubleBounce
        progressBar!!.visibility = View.VISIBLE
        //mProgressBar.setBackgroundResource(R.drawable.progress_animation);
        //animationDrawable = (AnimationDrawable)mProgressBar.getBackground();
        //mProgressBar.setVisibility(View.VISIBLE);
        //animationDrawable.start();
        alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun cancelProgress() {

        //mProgressBar.setVisibility(View.GONE);
        //animationDrawable.stop();
        progressBar!!.visibility = View.GONE
        alertDialog.dismiss()
    }

    companion object {
        internal lateinit var alertDialog: AlertDialog
        //private static AnimationDrawable animationDrawable;
        internal lateinit var dialogBuilder: AlertDialog.Builder
        private val mProgressBar: ImageView? = null
        //private static ProgressDialog progressBar;
        private var progressBar: ProgressBar? = null
        var appProgressBar: AppProgressBar? = null

        val instance: AppProgressBar
            get() {

                if (appProgressBar == null) {
                    appProgressBar = AppProgressBar()
                }
                return appProgressBar!!
            }
    }
}

