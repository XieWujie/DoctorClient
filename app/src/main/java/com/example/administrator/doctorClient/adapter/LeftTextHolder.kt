package com.example.administrator.doctorClient.adapter


import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.databinding.LeftLayoutTextBinding




class LeftTextHolder(val binding: LeftLayoutTextBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Message){
            binding.message = any
        }
    }
}