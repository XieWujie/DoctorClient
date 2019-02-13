package com.example.administrator.doctorClient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.services.help.Tip
import com.example.administrator.doctorClient.databinding.SearchItemBinding

class SearchAdapter:RecyclerView.Adapter<BaseHolder>(){

    private val mList = ArrayList<Tip>()
    private var listener:Event? = null

    fun setList(list: List<Tip>){
        mList.clear()
        mList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchHolder(binding)
    }

    fun setEvent(event: Event){
        if (listener == null){
            listener = event
        }
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(mList[position])
        holder.setEvent(listener)
    }
}