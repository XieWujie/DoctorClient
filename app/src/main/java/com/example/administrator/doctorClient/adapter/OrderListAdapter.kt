package com.example.administrator.doctorClient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.administrator.doctorClient.data.order.Order
import com.example.administrator.doctorClient.databinding.OrderListItemBinding

class OrderListAdapter:PagedListAdapter<Order,BaseHolder>(callback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = OrderListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderItemHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
      if (getItem(position)!=null){
          holder.bind(getItem(position)!!)
      }
    }


    companion object {

        private val callback = OrderDiffCallback()

        class OrderDiffCallback:DiffUtil.ItemCallback<Order>(){

            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }


        }
    }
}