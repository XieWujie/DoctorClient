package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.doctorClient.data.message.MessageRepository

class MessageModelFactory(private val messageRepository: MessageRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return MessageModel(messageRepository) as T
    }
}