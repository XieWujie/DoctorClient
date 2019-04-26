package com.example.administrator.doctorClient.utilities

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import java.io.File

object PathUtil{
    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }


    private fun getAvailableCacheDir(context: Context): File {
        return if (isExternalStorageWritable()) {
            context.externalCacheDir
        } else {
            context.cacheDir
        }
    }

    fun getAudioCachePath(context: Context, id: String): String? {
        return if (TextUtils.isEmpty(id)) null else File(getAvailableCacheDir(context), id).absolutePath
    }

    fun getRecordPathByCurrentTime(context: Context): String {
        return File(getAvailableCacheDir(context), "record_" + System.currentTimeMillis()).absolutePath
    }
}