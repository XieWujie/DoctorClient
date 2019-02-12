package com.example.administrator.doctorClient.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.administrator.doctorClient.data.message.Message

class MessageDiffCallback: DiffUtil.ItemCallback<Message>(){
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }


}