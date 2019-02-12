package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.data.message.MessageRepository

class MessageModel internal constructor(private val repository:MessageRepository):ViewModel(){

    private val config =  PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(20)
        .build()

    fun newMessage(owenerId:String) = LivePagedListBuilder<Int,Message>(repository.getNewMessage(owenerId),config).build()

    fun getMessage(id:String) = LivePagedListBuilder<Int,Message>(repository.queryById(id),config)
        .build()
}