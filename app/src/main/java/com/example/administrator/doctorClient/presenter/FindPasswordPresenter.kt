package com.example.administrator.doctorClient.presenter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.RequestPasswordResetCallback
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.StartActivity

class FindPasswordPresenter(@Bindable var mailBox:String):BaseObservable(){

    fun find(view: View){
        if (mailBox.isNullOrBlank()){
            Util.log(view,"邮箱不能为空")
        }
        AVUser.requestPasswordResetInBackground(mailBox,object :RequestPasswordResetCallback(){

            override fun done(e: AVException?) {
                if (e == null){
                    Util.toActivity<StartActivity>(view.context)
                }else{
                    Util.log(view,e.message)
                }
            }
        })
    }
}