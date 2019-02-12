package com.example.administrator.doctorClient.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.administrator.doctorClient.adapter.OrderListAdapter
import com.example.administrator.doctorClient.core.OrderManage
import com.example.administrator.doctorClient.databinding.FragmentOrderListBinding
import com.example.administrator.doctorClient.utilities.*
import com.example.administrator.doctorClient.viewmodel.OrderModel

class OrderListFragment:Fragment(){

    private lateinit var binding:FragmentOrderListBinding
    private val adapter = OrderListAdapter()
    private lateinit var model:OrderModel
    val handler = Handler()
    private var ownerId:String? = null
    private var type:Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrderListBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        val factory = ViewModelFactory.getOrderModelFactory(requireContext())
        model = ViewModelProviders.of(this,factory)[OrderModel::class.java]
        initView()
        return binding.root
    }

    private fun initView(){
        ownerId = arguments?.getString("ownerId")
        type = arguments?.getInt("type",-1)?:-1
        val recyclerview = binding.recyclerview
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = adapter
        if (ownerId !=null)
        when(type){
            ALL->model.getOwnerOrder(ownerId!!).observe(this, Observer {
                adapter.submitList(it)
            })
            NOT_EVALUATION, COMPLETE, NOT_GENERATED->model.getTypeOrder(ownerId!!,type).observe(this, Observer {
                adapter.submitList(it)
            })
            STARTING->model.getStartOrder(ownerId!!,Util.getCurrentTimeStamp()).observe(this, Observer {
                adapter.submitList(it)
            })
            NOT_START->model.getNotStartOrder(ownerId!!,Util.getCurrentTimeStamp()).observe(this, Observer {
                adapter.submitList(it)
            })
        }
        binding.freshLayout.setOnRefreshListener {
            OrderManage.requestUserOrder(requireContext(),ownerId!!){
                binding.freshLayout.isRefreshing = false
                if (it != null){
                    Util.log(binding.root,it.message)
                }
            }
        }
    }
}