package com.example.administrator.doctorClient.data

import java.io.Serializable

data class Position(
    val country:String,
    val province:String,
    val city:String,
    val district:String,
    val streetNumber:String,
    val description:String,
    val latitude:Double,
    val longitude:Double
):Serializable{

    override fun toString(): String {
        return "$province$city$district$streetNumber$description"
    }
}