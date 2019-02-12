package com.example.administrator.doctorClient.data.user

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.administrator.doctorClient.data.Position

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val userId:String,
    val name:String,
    val password:String,
    val loginTime:Long,
    val mailBox:String,
    var isLogout:Boolean = true,
    var avatar:String?,
    @Embedded val  position: Position,
    val qualification:String,
    val education:String,
    val doctorCertification:Boolean,
    val authentication:Boolean,
    val workerTime:String,
    val graduatedSchool:String,
    val workerAddress:String,
    val phone:String,
    val historyOrderCount:Int,
    val goodAt:String
)