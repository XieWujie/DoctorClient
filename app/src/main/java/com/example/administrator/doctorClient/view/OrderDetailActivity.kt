package com.example.administrator.doctorClient.view

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
            binding.order = newOrder
            eventHandle(newOrder)
            initSchedule(newOrder)
        }
        setTitle("订单详情")
    }

    private fun initSchedule(order: Order){
        val f = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val state = order.state
        val schedule = binding.orderSchedule
        when(state){
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

    fun eventHandle(order: Order){
        binding.sendMessage.setOnClickListener {
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra(CONVERSATION__NAME,order.name)
            intent.putExtra(CONVERSATION_ID,order.doctorId)
            intent.putExtra(AVATAR,order.name)
            startActivity(intent)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        val p = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT)
        p.weight = 1f
        when(order.state){
            NOT_GENERATED->{
                val agree = LayoutInflater.from(this).inflate(R.layout.order_bottom_button,null) as Button
                val notAgree = LayoutInflater.from(this).inflate(R.layout.order_bottom_button,null) as Button
                agree.text = "同意"
                notAgree.text = "不同意"
                binding.bottomLayout.addView(notAgree,p)
                binding.bottomLayout.addView(agree,p)
                agree.setOnClickListener {
                    val dialog = Util.createProgressDialog(this)
                    dialog.show()
                    OrderManage.agreeOrder(this,order){e->
                        dialog.dismiss()
                        if (e == null){
                            binding.bottomLayout.removeView(notAgree)
                            agree.text = "已同意"
                            agree.isClickable = false
                            initSchedule(order)
                        }else{
                            Util.log(binding.root,e.message)
                        }
                    }
                }
                notAgree.setOnClickListener {
                    val dialog = Util.createProgressDialog(this)
                    dialog.show()
                    OrderManage.cancelOrder(order){e->
                        dialog.dismiss()
                        if (e == null){
                            binding.bottomLayout.removeView(agree)
                            binding.bottomLayout.removeView(notAgree)
                        }else{
                            Util.log(binding.root,e.message)
                        }
                    }
                }
            }
            STARTING->{
                val button  = LayoutInflater.from(this).inflate(R.layout.order_bottom_button,null) as Button
                button.text = "确认支付"
                binding.bottomLayout.addView(button,p)
                button.setOnClickListener {
                    OrderManage.endTreatment(this,order){e->
                        if (e == null){
                            button.text = "已确认"
                            initSchedule(order)
                        }else{
                            Util.log(it,e.message)
                        }
                    }
                    UserManage.updateHistoryCount(this){}
                }
            }
        }
    }
}
