package com.example.administrator.doctorClient.data.evaluation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Evaluation(
    @PrimaryKey val id:String,
    val createAt:Long,
    val patientId:String,
    val doctorId:String,
    val score:Float,
    val avatar:String?,
    val name:String,
    val content:String
)