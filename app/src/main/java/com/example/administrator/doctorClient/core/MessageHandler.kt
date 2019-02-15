package com.example.administrator.doctorClient.core

import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMConversation
import com.avos.avoscloud.im.v2.AVIMMessage
import com.avos.avoscloud.im.v2.AVIMMessageHandler
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage
import com.example.administrator.doctorClient.App
import com.example.administrator.doctorClient.custom.OrderMessage
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.utilities.*

class MessageHandler: AVIMMessageHandler(){

    override fun onMessage(message: AVIMMessage?, conversation: AVIMConversation?, client: AVIMClient?) {
        when (message) {
            is AVIMTextMessage -> cacheTextMessage(message, conversation!!)
            is AVIMImageMessage -> cacheImageMessage(message, conversation!!)
            is AVIMVideoMessage ->handlerVoiceMessage(message,conversation!!)
            is OrderMessage ->OrderManage.requestOneOrder(App.getContext(),message.id){}
        }
    }

    private fun handlerVoiceMessage(m:AVIMVideoMessage,c:AVIMConversation){
        cacheMessage(m,c,m.localFilePath?:m.fileUrl, VOICE_MESSAGE,m.duration)
    }


    private fun cacheMessage(m:AVIMMessage,c:AVIMConversation,content:String,type:Int,voiceTime:Double = 0.0){
        val id = m.from
        MessageManage.findMessageById(id){ avatar, name->
            val message = Message(m.messageId,c.conversationId,name,content,name,id,
                type,voiceTime, c.unreadMessagesCount,m.timestamp,UserManage.user?.userId!!, SENDING,avatar)
            MessageManage.cacheMessage(message,false)
            NotifyUtil.createNewMessageNotification(App.getContext(),message)
        }
    }



    private fun cacheTextMessage( m: AVIMTextMessage,c: AVIMConversation){
        cacheMessage(m,c,m.text, TEXT_MESSAGE)
    }

    private fun cacheImageMessage(m:AVIMImageMessage,c: AVIMConversation){
        cacheMessage(m,c,m.fileUrl, IMAGE_MESSAGE)
    }
}