package com.example.administrator.doctorClient.data.doctor

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Patient(
    @PrimaryKey
     val id:String,
    val name:String,
    val avatar:String?
):Serializable