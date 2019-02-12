package com.example.administrator.doctorClient.adapter

import com.example.administrator.doctorClient.data.evaluation.Evaluation
import com.example.administrator.doctorClient.databinding.CommentItemBinding

class CommentHolder(val binding:CommentItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Evaluation){
            binding.comment = any
        }
    }
}