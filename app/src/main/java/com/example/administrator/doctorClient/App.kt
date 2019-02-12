package com.example.administrator.doctorClient

import android.app.Application
import android.content.Context
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.im.v2.AVIMMessageManager
import com.example.administrator.doctorClient.core.MessageHandler
import com.example.administrator.doctorClient.custom.OrderMessage

private lateinit var context: Context
class App:Application(){

//     val APP_ID = "FTDVimcFscVf6mA2n9ebuCue-gzGzoHsz"
//     val APP_KEY = "eOK5mMX27ts5zjfhFDrQTVTc"
    val APP_ID = "i2TacWout59nxbKyt0NawBHJ-gzGzoHsz"
    val APP_KEY = "kxp8G4U9vux2zuv68rrm2sV3"

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AVOSCloud.initialize(this, APP_ID, APP_KEY)
        AVOSCloud.setDebugLogEnabled(true)
        AVIMMessageManager.registerDefaultMessageHandler(MessageHandler())
        AVIMMessageManager.registerAVIMMessageType(OrderMessage::class.java)
    }

    companion object {

        fun getContext() = context
    }
}