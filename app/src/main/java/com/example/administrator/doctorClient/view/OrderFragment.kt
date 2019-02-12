package com.example.administrator.doctorClient.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.administrator.doctorClient.adapter.FragmentViewPagerAdapter
import com.example.administrator.doctorClient.core.OrderManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.FragmentOrderBinding
import com.example.administrator.doctorClient.utilities.ALL
import com.example.administrator.doctorClient.utilities.COMPLETE
import com.example.administrator.doctorClient.utilities.Util


class OrderFragment : Fragment(), ViewPager.OnPageChangeListener{

    lateinit var binding: FragmentOrderBinding
    var displayWidth = 0
    var width = 0
    var leftMargin = 0
    private lateinit var layoutParams: LinearLayout.LayoutParams


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrderBinding.inflate(inflater,container,false)
        initUI()
        dispatchEvent()
        return binding.root
    }

    private fun dispatchEvent() {
        val viewPager = binding.viewPager
        binding.all.setOnClickListener {
            viewPager.currentItem = 0
        }
        binding.notGenerate.setOnClickListener {
            viewPager.currentItem = 1
        }
        binding.notStart.setOnClickListener {
            viewPager.currentItem = 2
        }
        binding.starting.setOnClickListener {
            viewPager.currentItem = 3
        }
        binding.complete.setOnClickListener {
            viewPager.currentItem = 5
        }
        binding.notEvaluation.setOnClickListener {
            viewPager.currentItem = 4
        }
    }

    fun initUI(){
        activity?.setTitle("订单")
        val ownerId = UserManage.user?.userId
        if (ownerId == null){
            Util.log(activity!!.window.decorView,"请先登陆")
            return
        }
        OrderManage.requestUserOrder(requireContext(),ownerId){
            if (it != null){
                Util.log(activity!!.window.decorView,"网络请求订单失败")
            }
        }
        layoutParams = binding.tab.layoutParams as LinearLayout.LayoutParams
        val tabWidth = layoutParams.width
        displayWidth = activity!!.windowManager.defaultDisplay.width
        width = displayWidth/6
        leftMargin = width/2 - tabWidth/2
        layoutParams.leftMargin = leftMargin
        binding.viewPager.setOnPageChangeListener(this)
        val list = ArrayList<OrderListFragment>()
        for (i in ALL until  COMPLETE +1){
            val fragment = OrderListFragment()
            list.add(fragment)
            val bundle = Bundle()
            bundle.putString("ownerId",ownerId)
            bundle.putInt("type",i)
            fragment.arguments = bundle
        }
        val adapter = FragmentViewPagerAdapter(activity!!.supportFragmentManager,list)
        binding.viewPager.adapter = adapter
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        layoutParams.leftMargin = (leftMargin+(position + positionOffset)*width ).toInt()
        binding.tab.layoutParams = layoutParams
    }

    override fun onPageSelected(position: Int) {

    }
}
