package com.example.administrator.doctorClient.presenter

import android.view.View
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.RequestPasswordResetCallback
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.utilities.Util

class AcountAndSafetyPresenter{

    fun resetPassword(view: View){
        val mailBox = UserManage.user?.mailBox
        if (mailBox == null){
            Util.log(view,"请先登陆")
            return
        }
        AVUser.requestPasswordResetInBackground(mailBox,object : RequestPasswordResetCallback(){

            override fun done(e: AVException?) {
                if (e == null){
                    Util.log(view,"已发送邮件至$mailBox,请前往更改密码")
                }else{
                    Util.log(view,e.message)
                }
            }
        })
    }
}