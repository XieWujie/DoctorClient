package com.example.administrator.doctorClient.data.message

import com.example.administrator.doctorClient.utilities.runOnNewThread

class MessageRepository private constructor(private val messageDao: MessageDao){

    fun queryById(id:String) = messageDao.queryById(id)

    fun insert(message: Message){
        runOnNewThread {
            messageDao.insert(message)
        }
    }

    fun insert(messages:List<Message>){
        runOnNewThread {
            messageDao.insert(messages)
        }
    }

    fun update(message: Message){
        runOnNewThread {
            messageDao.update(message)
        }
    }

    fun delete(conversationId:String){
        runOnNewThread {
            messageDao.delete(conversationId)
        }
    }

    fun delete(message: Message){
        runOnNewThread {
            messageDao.delete(message)
        }
    }

    fun getNewMessage(owner:String) = messageDao.getNewMessage(owner)

    companion object {

        @Volatile private var instance:MessageRepository? = null

        fun getInstance(messageDao: MessageDao):MessageRepository{
            return instance?: synchronized(this){
                instance?:MessageRepository(messageDao).also { instance = it }
            }
        }
    }
}