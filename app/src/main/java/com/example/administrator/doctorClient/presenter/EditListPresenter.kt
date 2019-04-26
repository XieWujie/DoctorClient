package com.example.administrator.doctorClient.presenter

import android.content.Intent
import android.view.View
import com.example.administrator.doctorClient.utilities.AVATAR
import com.example.administrator.doctorClient.utilities.USER_NAME
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.EditItemActivity
import com.example.administrator.doctorClient.view.PositionActivity

class EditListPresenter{

    fun userName(view: View){
        toEditItemActivity(USER_NAME,view)
    }



    fun mailBox(view: View){
        toEditItemActivity("email",view)
    }
    fun avatar(view: View){
        toEditItemActivity(AVATAR,view)
    }
    fun  position(view: View){
        Util.toActivity<PositionActivity>(view.context)
    }

    fun qualification(view: View){
        toEditItemActivity("qualification",view)
    }


    fun workTime(view: View){
        toEditItemActivity("workTime",view)
    }
    fun graduatedSchool(view: View){
        toEditItemActivity("graduatedSchool",view)
    }

    fun phone(view: View){
        toEditItemActivity("phone",view)
    }

    fun goodAt(view: View){
        toEditItemActivity("goodAt",view)
    }

    private fun toEditItemActivity(key:String, view: View){
        val context = view.context
        val intent = Intent(context,EditItemActivity::class.java)
        intent.putExtra("key",key)
        context.startActivity(intent)
    }
}