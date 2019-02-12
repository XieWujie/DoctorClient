package com.example.administrator.doctorClient.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.administrator.doctorClient.adapter.MessageListAdapter
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.FragmentMessageListBinding
import com.example.administrator.doctorClient.utilities.ViewModelFactory
import com.example.administrator.doctorClient.viewmodel.MessageModel

class MessageListFragment:Fragment(){

    private lateinit var binding:FragmentMessageListBinding
    private lateinit var model:MessageModel
    private val adapter = MessageListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMessageListBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        val factory = ViewModelFactory.getMessageModelFactory(requireContext())
        model = ViewModelProviders.of(this,factory).get(MessageModel::class.java)
        binding.messageRc.layoutManager = LinearLayoutManager(requireContext())
        binding.messageRc.adapter = adapter
        getMessage()
        binding.freshLayout.setOnRefreshListener {
            getMessage()
        }
        return binding.root
    }

    private fun getMessage(){
        val id = UserManage.user?.userId
        if (id == null){
            binding.freshLayout.isRefreshing = false
            return
        }
        MessageManage.freshMessage()
        model.newMessage(id).observe(this, Observer {
            adapter.submitList(it)
            Log.d("uiui",it.toString())
            binding.freshLayout.isRefreshing = false
        })
    }

    override fun onStart() {
        super.onStart()
        activity?.setTitle("消息")
    }
}