package com.example.administrator.doctorClient.data.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "order")
data class Order(
    @PrimaryKey val id:String,
    val patientId:String,
    val doctorId:String,
    val orderTime:Long,
    val description:String,
    val state:Int,
    val avatar:String?,
    val name:String,
    val score:Float,
    val agreeTime:Long,
    val createTime:Long,
    val completeTime:Long,
    val endTreatmentTime:Long
):Serializable