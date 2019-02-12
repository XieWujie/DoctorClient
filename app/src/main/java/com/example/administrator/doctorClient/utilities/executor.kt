package com.example.administrator.doctorClient.utilities

import java.util.concurrent.Executors

private val executor = Executors.newSingleThreadExecutor()

fun runOnNewThread(f:()->Unit){
    executor.submit(f)
}

fun getKey(value1:String,value2:String):String{
    return (value1+value2)
}