package com.example.administrator.doctorClient.adapter

import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.databinding.LeftLayoutImageBinding


class LeftImageHolder(val bind:LeftLayoutImageBinding):BaseHolder(bind.root){

    override fun bind(any: Any) {
        if (any is Message){
            bind.message = any
        }
    }
}