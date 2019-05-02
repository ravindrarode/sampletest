package com.iserve.retrofitdemo.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class AppTextView : TextView {
    constructor(paramContext: Context) : super(paramContext) {
        init()
    }

    constructor(paramContext: Context, paramAttributeSet: AttributeSet) : super(paramContext, paramAttributeSet) {
        init()
    }

    constructor(paramContext: Context, paramAttributeSet: AttributeSet, paramInt: Int) : super(paramContext, paramAttributeSet, paramInt) {
        init()
    }

    fun init() {
        typeface = Typeface.createFromAsset(context.assets, "Roboto-Light.ttf")
    }
}