package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.administrator.doctorClient.data.order.Order
import com.example.administrator.doctorClient.data.order.OrderRepository

class OrderModel internal constructor(private val respository:OrderRepository):ViewModel(){

    private val config =  PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(20)
        .build()

    fun getOwnerOrder(ownerId:String) = LivePagedListBuilder<Int,Order>(respository.getOwnerOrder(ownerId),config).build()

    fun getTypeOrder(ownerId: String,type:Int) = LivePagedListBuilder<Int,Order>(respository.getTypeOrder(ownerId,type),config).build()

    fun getStartOrder(ownerId: String,time:Long) = LivePagedListBuilder<Int,Order>(respository.getStartOrder(ownerId,time),config).build()


    fun getNotStartOrder(ownerId: String,time:Long) = LivePagedListBuilder<Int,Order>(respository.getNotStartOrder(ownerId,time),config).build()
}