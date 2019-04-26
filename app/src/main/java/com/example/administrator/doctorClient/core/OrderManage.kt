package com.example.administrator.doctorClient.core

import android.content.Context
import android.util.Log
import com.avos.avoscloud.*
import com.example.administrator.doctorClient.data.AppDatabase
import com.example.administrator.doctorClient.data.order.Order
import com.example.administrator.doctorClient.data.order.OrderRepository
import com.example.administrator.doctorClient.utilities.NOT_EVALUATION
import com.example.administrator.doctorClient.utilities.NOT_START
import java.util.*


object OrderManage{

    private var respository:OrderRepository? = null


    private fun initRepository(context: Context){
        if (respository == null)
        respository = OrderRepository.getInstance(AppDatabase.getInstance(context).getOrder())
    }

    fun requestOneOrder(context: Context,orderId:String,requestCallback: (e: Exception?) -> Unit){
        initRepository(context)
        val o = AVObject.createWithoutData("Order",orderId)
        o.fetchInBackground(object :GetCallback<AVObject>(){
            override fun done(o: AVObject?, e: AVException?) {
                if (e == null) {
                    with(o!!) {
                        val doctorId = getString("doctorId")
                        if (doctorId == null) {
                            requestCallback(null)
                            return
                        }
                        PatientManager.findPatientById(context, doctorId) {
                            if (it == null) {
                                requestCallback(null)
                                return@findPatientById
                            } else {
                                val avatar = it.avatar
                                val name = it.name
                                val time = getLong("time")
                                val description = getString("description")
                                val state = getInt("state")
                                val patientId: String = getString("patientId")
                                val score = getDouble("score").toFloat()
                                val agreeTime = getLong("agreeTime")
                                val completeTime = getLong("completeTime")
                                val createTime = createdAt.time
                                val endTreatmentTime = o.getLong("endTreatmentTime")
                                val order = Order(
                                    objectId, patientId, doctorId, time, description, state, avatar,
                                    name,  score,  agreeTime, createTime, completeTime,endTreatmentTime
                                )
                                respository?.addOrder(order)
                            }
                        }
                    }
                }else{
                    requestCallback(e)
                }
            }
        })
    }

    fun requestUserOrder(context: Context, doctorId:String, requestCallback:(e:Exception?)->Unit){
        initRepository(context)
        val q = AVQuery<AVObject>("Order")
        q.whereEqualTo("doctorId",doctorId)
        q.findInBackground(object :FindCallback<AVObject>(){
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null){
                    val size = list!!.size
                    var count = 0
                    list.forEach { it ->
                        count++
                        with(it) {
                            val patientId = getString("patientId")
                            if (patientId == null){
                                requestCallback(null)
                                return
                            }
                            PatientManager.findPatientById(context,patientId){
                                if (it == null){
                                    requestCallback(null)
                                    return@findPatientById
                                }else{
                                    val avatar = it.avatar
                                    val name = it.name
                                    val time = getLong("time")
                                    val description = getString("description")
                                    val state = getInt("state")
                                    val score = getDouble("score").toFloat()
                                    val agreeTime = getLong("agreeTime")
                                    val completeTime = getLong("completeTime")
                                    val createTime = createdAt.time
                                    val endTreatmentTime = getLong("endTreatmentTime")
                                    val order = Order(objectId,patientId,doctorId,time,description,state,avatar,
                                        name,score,agreeTime,createTime,completeTime,endTreatmentTime)
                                    Log.d("order-",order.toString())
                                    respository?.addOrder(order)
                                    if (count == size){
                                        requestCallback(null)
                                        return@findPatientById
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    fun endTreatment(context:Context,order:Order,changeCallback:(e:Exception?)->Unit){
        initRepository(context)
        val o = AVObject.createWithoutData("Order",order.id)
        o.put("state", NOT_EVALUATION)
        val endTreatmentTime = Date().time
        o.put("endTreatmentTime",endTreatmentTime)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    respository?.addOrder(order.copy(state = NOT_EVALUATION,endTreatmentTime = endTreatmentTime))
                    MessageManage.sendOrderMessage(order.patientId,order.id)
                    changeCallback(null)
                }else{
                    changeCallback(e)
                }
            }
        })
    }

    fun cancelOrder(order: Order,cancelCallback:(e:Exception?)->Unit){
        val o = AVObject.createWithoutData("Order",order.id)
            o.deleteInBackground(object :DeleteCallback(){
                override fun done(e: AVException?) {
                    if (e == null){
                        respository?.delete(order)
                        cancelCallback(null)
                    }else{
                        cancelCallback(e)
                    }
                }
            })
    }

    fun agreeOrder(context:Context,order:Order,agreeOrderCallback:(e:Exception?)->Unit){
        initRepository(context)
        val o = AVObject.createWithoutData("Order",order.id)
        o.put("state", NOT_START)
        val agreeTime = Date().time
        o.put("endTreatmentTime",agreeTime)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    respository?.addOrder(order.copy(state = NOT_START,agreeTime = agreeTime))
                    MessageManage.sendOrderMessage(order.doctorId,order.id)
                    agreeOrderCallback(null)
                }else{
                   agreeOrderCallback(e)
                }
            }
        })
    }

}