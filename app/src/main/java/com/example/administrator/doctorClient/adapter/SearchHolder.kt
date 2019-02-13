package com.example.administrator.doctorClient.adapter

import com.amap.api.services.help.Tip
import com.example.administrator.doctorClient.databinding.SearchItemBinding

class SearchHolder(val binding:SearchItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Tip){
            binding.result = any.name
            binding.root.setOnClickListener {
                listenter?.event(any)
            }
        }
    }
}