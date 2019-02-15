package com.example.administrator.doctorClient.presenter


import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.administrator.doctorClient.data.user.User
import com.example.administrator.doctorClient.utilities.CONVERSATION_ID
import com.example.administrator.doctorClient.utilities.CONVERSATION__NAME
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.*

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
        val context = view.context
        val builder = AlertDialog.Builder(context)
        val a = arrayOf("拨打电话","发送消息")
        builder.setItems(a){d,w->
            when(w){
                0->{
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:13677626587"))
                    context.startActivity(intent)
                }
                1->{
                    val id = "5c66404f303f390047fd1867"
                    val intent = Intent(context,ChatActivity::class.java)
                    intent.putExtra(CONVERSATION__NAME,"客服")
                    intent.putExtra(CONVERSATION_ID,id)
                    context.startActivity(intent)
                }
            }
        }.setCancelable(true).show()
    }

    fun clickSetting(view: View){
        Util.toActivity<SettingActivity>(view.context)
    }
}