package com.example.administrator.doctorClient.data.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    @PrimaryKey()
    val id:String,
    val conversationId:String,
    val conversationName:String,
    val message:String,
    val fromName:String,
    val fromId:String,
    val type:Int,
    val voiceTime:Double,
    val unReadCount:Int,
    val createAt:Long,
    val owner:String,
    val sendState:Int,
    val avatar:String?
)