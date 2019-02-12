package com.example.administrator.doctorClient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.databinding.MessageItemBinding

class MessageListAdapter:PagedListAdapter<Message,BaseHolder>(MessageDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageListHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
       holder.bind(getItem(position)!!)
    }
}