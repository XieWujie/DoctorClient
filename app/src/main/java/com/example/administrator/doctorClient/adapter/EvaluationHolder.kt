package com.example.administrator.doctorClient.adapter

import com.example.administrator.doctorClient.data.evaluation.Evaluation
import com.example.administrator.doctorClient.databinding.EvaluationItemBinding

class EvaluationHolder(val binding:EvaluationItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Evaluation){
            binding.evaluation = any
        }
    }
}