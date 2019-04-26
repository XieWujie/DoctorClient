package com.example.administrator.doctorClient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.databinding.*
import com.example.administrator.doctorClient.utilities.IMAGE_MESSAGE
import com.example.administrator.doctorClient.utilities.TEXT_MESSAGE
import com.example.administrator.doctorClient.utilities.VOICE_MESSAGE

class ChatAdapter:PagedListAdapter<Message,BaseHolder>(MessageDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            TYPE_TEXT_RIGHT ->RightTextHolder(RightLayoutTextBinding.inflate(inflater,parent,false))
            TYPE_TEXT_LEFT ->LeftTextHolder(LeftLayoutTextBinding.inflate(inflater,parent,false))
            TYPE_IMAGE_RIGHT ->RightImageHolder(RightLayoutImageBinding.inflate(inflater,parent,false))
            TYPE_IMAGE_LEFT ->LeftImageHolder(LeftLayoutImageBinding.inflate(inflater,parent,false))
            TYPE_VOICE_RIGHT ->RightVoiceHolder(RightVoiceLayoutBinding.inflate(inflater,parent,false))
            TYPE_VOICE_LEFT ->LeftVoiceHolder(LeftLayoutVoiceBinding.inflate(inflater,parent,false))
            else ->throw Throwable("have not find this holder")
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val item = getItem(position)
        if (item!=null){
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val id = UserManage.user?.userId
        return getItem(position)!!.let {
            when(it.type){
                TEXT_MESSAGE ->if (it.fromId == id) TYPE_TEXT_RIGHT else TYPE_TEXT_LEFT
                IMAGE_MESSAGE ->if (it.fromId == id) TYPE_IMAGE_RIGHT else TYPE_IMAGE_LEFT
                VOICE_MESSAGE->if (it.fromId == id) TYPE_VOICE_RIGHT else TYPE_VOICE_LEFT
                else ->throw Throwable("have not find this type")
            }
        }
    }

    companion object {

        const val TYPE_TEXT_RIGHT = 10
        const val TYPE_TEXT_LEFT = 11
        const val TYPE_IMAGE_RIGHT = 12
        const val TYPE_IMAGE_LEFT = 13
        const val TYPE_VOICE_RIGHT = 14
        const val TYPE_VOICE_LEFT = 15
    }
}