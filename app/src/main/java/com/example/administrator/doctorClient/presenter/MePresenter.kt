package com.example.administrator.doctorClient.presenter


import android.view.View
import com.example.administrator.doctorClient.data.user.User
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.EditListActivity
import com.example.administrator.doctorClient.view.CommentActivity
import com.example.administrator.doctorClient.view.ProveActivity
import com.example.administrator.doctorClient.view.SettingActivity

class MePresenter{

    fun prove(view:View){
        Util.toActivity<ProveActivity>(view.context)
    }

    fun comment(view: View){
        Util.toActivity<CommentActivity>(view.context)
    }

    fun edit(view: View){
        Util.toActivity<EditListActivity>(view.context)
    }

    fun clickSafety(view: View){

    }

    fun clickCustomer(view: View){

    }

    fun clickSetting(view: View){
        Util.toActivity<SettingActivity>(view.context)
    }
}