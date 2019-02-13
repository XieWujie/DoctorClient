package com.example.administrator.doctorClient.presenter

import android.content.Intent
import android.view.View
import androidx.navigation.findNavController
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.StartActivity


class SettingPresenter{

    fun acountAndSafety(view: View){
        view.findNavController().navigate(R.id.action_settingFragment_to_acountAndSafetyFragment)
    }

    fun switchLanguage(view: View){

    }

    fun clearCache(view: View){

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