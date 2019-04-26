package com.example.administrator.doctorClient.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder(val view:View):RecyclerView.ViewHolder(view){

    protected var listenter:Event? = null
    protected val context = view.context!!

    abstract fun bind(any:Any)

    fun setEvent(event: Event?){
        if (listenter == null){
            listenter = event
        }
    }

}

interface Event{

    fun event(vararg any: Any)
}