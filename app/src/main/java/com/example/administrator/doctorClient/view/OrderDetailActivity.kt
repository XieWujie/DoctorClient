package com.example.administrator.doctorClient.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.OrderManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.order.Order
import com.example.administrator.doctorClient.databinding.ActivityOrderDetailBinding
import com.example.administrator.doctorClient.utilities.*
import java.text.SimpleDateFormat
import java.util.*


class OrderDetailActivity : BaseActivity() {

    private lateinit var binding:ActivityOrderDetailBinding
    private var menu: Menu? = null
    private val AGREE = 1
    private val DISAGREE = 2
    private val CONFIRM = 3
    private var order:Order? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_detail)
        setActionBar(binding.toolbar)
        val order = intent.getSerializableExtra("order")
        if (order is Order){
            val newOrder =  if (order.state == NOT_START&&Util.getCurrentTimeStamp()>order.orderTime){
                order.copy(state = STARTING)
            }else{
                order
            }
            this.order = newOrder
            binding.order = newOrder
            initSchedule(newOrder)
        }
        title = "订单详情"
    }

    private fun initSchedule(order: Order){
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val schedule = binding.orderSchedule
        when(order.state){
            NOT_GENERATED->schedule.init(0, listOf(f.format(Date(order.createTime))))
            NOT_START->schedule.init(1, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime))))
            STARTING->schedule.init(2, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime)),
                f.format(Date(order.orderTime))))
            NOT_EVALUATION->schedule.init(3, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime)),
                f.format(Date(order.orderTime)),f.format(Date(order.endTreatmentTime))))
            COMPLETE->schedule.init(4, listOf(f.format(Date(order.createTime)),f.format(Date(order.agreeTime)),
                f.format(Date(order.orderTime)),f.format(Date(order.endTreatmentTime)),f.format(Date(order.completeTime))))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        MenuInflater(this).inflate(R.menu.order_detail_menu,menu)
            when(order?.state){
                NOT_GENERATED->{
                    menu?.add(1, AGREE,2,"同意")
                    menu?.add(1,DISAGREE,3,"不同意")
                }
                STARTING->{
                    menu?.add(1,CONFIRM,2,"确认支付")
                }
            }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (order == null)return false
        when(item?.itemId){
            AGREE->{
                val dialog = Util.createProgressDialog(this)
                dialog.show()
                OrderManage.agreeOrder(this,order!!){e->
                    dialog.dismiss()
                    if (e == null){
                        menu?.removeItem(AGREE)
                        menu?.removeItem(DISAGREE)
                    }else{
                        Util.log(binding.root,e.message)
                    }
                }
            }
            DISAGREE->{
                val dialog = Util.createProgressDialog(this)
                dialog.show()
                OrderManage.cancelOrder(order!!){e->
                    dialog.dismiss()
                    if (e == null){

                    }else{
                        Util.log(binding.root,e.message)
                    }
                }
            }
            CONFIRM->{
                OrderManage.endTreatment(this,order!!){e->
                    if (e == null){
                        menu?.removeItem(CONFIRM)
                    }else{
                        Util.log(binding.root,e.message)
                    }
                }
                UserManage.updateHistoryCount(this){}
            }
            R.id.send_message->{
                val intent = Intent(this,ChatActivity::class.java)
                intent.putExtra(CONVERSATION__NAME,order!!.name)
                intent.putExtra(CONVERSATION_ID,order!!.doctorId)
                startActivity(intent)
            }
        }
        return true
    }
}
