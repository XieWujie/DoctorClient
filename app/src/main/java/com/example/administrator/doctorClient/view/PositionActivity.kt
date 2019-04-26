package com.example.administrator.doctorClient.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.services.district.DistrictSearch
import com.amap.api.services.district.DistrictSearchQuery
import com.amap.api.services.help.Inputtips
import com.amap.api.services.help.InputtipsQuery
import com.amap.api.services.help.Tip
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.adapter.Event
import com.example.administrator.doctorClient.adapter.SearchAdapter
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.Position
import com.example.administrator.doctorClient.databinding.ActivityPositionBinding
import com.example.administrator.doctorClient.utilities.Util


class PositionActivity : BaseActivity(),SearchView.OnQueryTextListener{

    private var position = Position("","","","","","未知",0.0,0.0)
    private lateinit var binding:ActivityPositionBinding
    private val adapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_position)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
        auto()
        dispatchEvent()
    }

   private fun dispatchEvent(){
        binding.cancel.setOnClickListener {
            finish()
        }
       binding.auto.setOnClickListener {
           auto()
       }
       binding.manual.setOnClickListener {
           manual()
       }
       binding.search.setOnQueryTextListener(this)
       adapter.setEvent(object :Event{
           override fun event(vararg any: Any) {
               val tip = any[0]
               if (tip is Tip){
                   binding.search.setQuery(tip.name,true)
                   val point = tip.point
                   position = position.copy(latitude = point.latitude,longitude = point.longitude)
               }
           }
       })
       binding.save.setOnClickListener { it ->
           val description = binding.search.query.toString()
           if (description.isBlank()){
               Util.log(binding.root,"请添加评论")
           }else {
               UserManage.updatePosition(this, position.copy(description = description)){
                   if (it == null){
                       finish()
                   }else{
                       Util.log(binding.root,it.message)
                   }
               }
           }
       }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()){
            q(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrBlank()){
            q(newText)
        }
        return true
    }

    private fun q(keyword: String){
        val query = InputtipsQuery(keyword,position.streetNumber)
        query.cityLimit = true
        val tip = Inputtips(this,query)
        tip.setInputtipsListener{list,code->
            runOnUiThread {
                adapter.setList(list)
                adapter.notifyDataSetChanged()
            }
        }
        tip.requestInputtipsAsyn()
    }

    private fun auto(){
        val mCilient = AMapLocationClient(this)
        val   mLocationOption =  AMapLocationClientOption()
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption.isNeedAddress = true
        mLocationOption.isOnceLocation = true
        mLocationOption.isWifiScan = true
        mLocationOption.isMockEnable = false
        mCilient.setLocationOption(mLocationOption)
        mCilient.setLocationListener {aMapLocation ->
            with(aMapLocation){
                if (this == null || errorCode != 0){
                    return@with
                }
                position = Position(country,province,city,district,street,"",latitude,longitude)
                binding.show.text = position.toString()
            }
        }
        mCilient.startLocation()
    }

    private fun manual(){
        val builder = AlertDialog.Builder(this)
        val districtSearch =  DistrictSearch(this)
        val query = DistrictSearchQuery()
        query.keywords = "中华人民共和国"
        districtSearch.query = query
        manualQuery(districtSearch,query,builder)
    }

    private fun manualQuery(districtSearch: DistrictSearch, query: DistrictSearchQuery, builder:AlertDialog.Builder){
        districtSearch.setOnDistrictSearchListener { result ->
            val d = result.district[0]
            val a = d.subDistrict.map { it.name }.toTypedArray()
            val name = d.name
            position = when (d.level) {
                "country" -> position.copy(country = name)
                "province" -> position.copy(province = name)
                "city" -> position.copy(city = name)
                "district" -> position.copy(district = name)
                "street" -> position.copy(streetNumber = name,latitude = d.center.latitude,longitude = d.center.longitude)
                else -> position
            }
            if (a.isNotEmpty()) {
                builder.setItems(a) { d, p ->
                    query.keywords = a[p]
                    manualQuery(districtSearch, query, builder)
                }.show()
            }else{
                builder.show().dismiss()
                binding.show.text = position.toString()
            }
        }
        districtSearch.searchDistrictAsyn()
    }
}
