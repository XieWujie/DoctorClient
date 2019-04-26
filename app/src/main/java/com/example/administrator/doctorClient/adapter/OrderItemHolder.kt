package com.example.administrator.doctorClient.adapter

import android.content.Intent
import android.view.View
import com.example.administrator.doctorClient.core.OrderManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.order.Order
import com.example.administrator.doctorClient.databinding.OrderListItemBinding
import com.example.administrator.doctorClient.utilities.*
import com.example.administrator.doctorClient.view.ChatActivity
import com.example.administrator.doctorClient.view.OrderDetailActivity

class OrderItemHolder(private val binding:OrderListItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Order){
            binding.order = any
            binding.cancel.visibility = View.INVISIBLE
            binding.state.text =  when(any.state){
                NOT_GENERATED->{
                    binding.cancel.visibility = View.VISIBLE
                    binding.cancel.text = "同意"
                    "待确认"
                }
               NOT_START->if (Util.getCurrentTimeStamp()<any.orderTime){
                   "未开始"
               }else{
                   binding.cancel.visibility = View.VISIBLE
                   binding.cancel.text = "确认支付"
                   "已开始"
               }
               STARTING->{
                   binding.cancel.visibility = View.VISIBLE
                   binding.cancel.text = "确认支付"
                   "已开始"
               }
               COMPLETE->{
                   binding.cancel.visibility = View.INVISIBLE
                   "已完成"
               }
               NOT_EVALUATION->{
                   "待评价"
               }
               else->"未知"
            }
            binding.message.setOnClickListener {
                val intent = Intent(it.context,ChatActivity::class.java)
                intent.putExtra(CONVERSATION_ID,any.doctorId)
                intent.putExtra(CONVERSATION__NAME,any.name)
                intent.putExtra(AVATAR,any.avatar)
                it.context.startActivity(intent)
            }
            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context,OrderDetailActivity::class.java)
                intent.putExtra("order",any)
                context.startActivity(intent)
            }
            binding.cancel.setOnClickListener { it ->
                when(binding.cancel.text){
                    "取消订单"->{
                        val dialog = Util.createProgressDialog(it.context)
                        dialog.show()
                        OrderManage.cancelOrder(any){
                            dialog.dismiss()
                            if (it != null){
                                Util.log(binding.root,"取消失败")
                            }
                        }
                    }
                    "同意"->{
                       OrderManage.agreeOrder(context,any){

                       }
                    }
                    "确认支付"->{
                        OrderManage.endTreatment(context,any){

                        }
                        UserManage.updateHistoryCount(context){}
                    }
                }
            }
        }
    }
}