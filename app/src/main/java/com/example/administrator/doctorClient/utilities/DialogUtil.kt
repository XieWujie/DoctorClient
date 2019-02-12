package com.example.administrator.doctorClient.utilities

import android.content.Context

object DialogUtil{


    fun getWindowHeight(context: Context):Int{
        val res = context.resources
        val  displayMetrics = res.displayMetrics
        return displayMetrics.heightPixels;
    }
}