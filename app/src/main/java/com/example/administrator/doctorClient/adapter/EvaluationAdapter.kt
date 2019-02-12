package com.example.administrator.doctorClient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.administrator.doctorClient.data.evaluation.Evaluation
import com.example.administrator.doctorClient.databinding.EvaluationItemBinding

class EvaluationAdapter:PagedListAdapter<Evaluation,BaseHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = EvaluationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EvaluationHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    companion object {

        private val diffCallback = EvaluationDiffCallback()

        class EvaluationDiffCallback:DiffUtil.ItemCallback<Evaluation>(){
            override fun areItemsTheSame(oldItem: Evaluation, newItem: Evaluation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Evaluation, newItem: Evaluation): Boolean {
                return oldItem == newItem
            }
        }
    }
}