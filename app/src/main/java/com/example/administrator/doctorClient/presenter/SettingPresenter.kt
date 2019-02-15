package com.example.administrator.doctorClient.presenter

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.cache.GlideCacheUtil
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.StartActivity


class SettingPresenter{

    fun acountAndSafety(view: View){
        view.findNavController().navigate(R.id.action_settingFragment_to_acountAndSafetyFragment)
    }


    fun clearCache(view: View){
        val text = view.findViewById<TextView>(R.id.cacheSize)
        val context = view.context
        val size = GlideCacheUtil.getCacheSize(context)
        var isSucceed = false
        GlideCacheUtil.clearImageAllCache(context){e->
            if (e == null){
                isSucceed = true
            }else{
                isSucceed = true
                Util.log(view,"清除磁盘缓存失败")
            }
        }
        while (true){
            if (isSucceed){
                text.text = GlideCacheUtil.getCacheSize(context)
                Util.log(view,"清除成功")
                break
            }
        }
    }

    fun userGuide(view: View){

    }

    fun feedback(view: View){

    }

    fun versionUpdate(view: View){

    }

    fun about(view: View){

    }

    fun logout(view: View){
        UserManage.logout()
        MessageManage.logout()
        Util.toActivity<StartActivity>(view.context)
    }
}