package com.example.administrator.doctorClient.utilities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.example.administrator.doctorClient.R
import com.google.android.material.snackbar.Snackbar
import java.util.*


object Util{
    fun createProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context, R.style.progressBar_dialog_style)
        val progressBar = ProgressBar(context)
        dialog.setContentView(progressBar)
        dialog.setCancelable(false)
        return dialog
    }

    fun getCurrentTimeStamp():Long{
        val CD = Calendar.getInstance()
        val YY = CD.get(Calendar.YEAR)
        val MM = CD.get(Calendar.MONTH)
        val DD = CD.get(Calendar.DATE)
        val HH = CD.get(Calendar.HOUR)
        val m = CD.get(Calendar.MINUTE)
        val date = Date(YY-1900,MM,DD,HH,m)
        return date.time
    }

    fun log(view:View,message:String?){
        if (message==null || message.isNullOrBlank())return
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()
    }

    inline fun<reified T:Activity> toActivity(context: Context){
        val intent = Intent(context,T::class.java)
        context.startActivity(intent)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        if (contentUri.scheme == "file") {
            return contentUri.encodedPath
        } else {
            var cursor: Cursor? = null
            return try {
                val pro = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri, pro, null, null, null)
                if (null != cursor) {
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    cursor.getString(column_index)
                } else {
                    ""
                }
            } finally {
                cursor?.close()
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param view
     */
    fun hideSoftInput(context: Context, view: View?) {
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun setStatusBar(activity: Activity,color:Int) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }
}