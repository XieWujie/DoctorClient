package com.example.administrator.doctorClient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.services.district.DistrictResult
import com.amap.api.services.district.DistrictSearch
import com.amap.api.services.district.DistrictSearchQuery
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.Position
import com.example.administrator.doctorClient.databinding.ActivityPositionBinding
import com.example.administrator.doctorClient.utilities.Util


class PositionActivity : AppCompatActivity() {

    private var position = Position("","","","","","未知",0.0,0.0)
    private lateinit var binding:ActivityPositionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_position)
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
       binding.save.setOnClickListener {
           val description = binding.editText.text.toString()
           if (description.isNullOrBlank()){
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
                position = Position(country,province,city,district,streetNum,"",latitude,longitude)
                binding.editLayout.hint = position.toString()
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

    fun manualQuery(districtSearch: DistrictSearch,query: DistrictSearchQuery,builder:AlertDialog.Builder){
        districtSearch.setOnDistrictSearchListener { result ->
            val d = result.district[0]
            val a = d.subDistrict.map { it.name }.toTypedArray()
            val name = d.name
            position = when (d.level) {
                "country" -> position.copy(country = name)
                "province" -> position.copy(province = name)
                "city" -> position.copy(city = name)
                "district" -> position.copy(district = name)
                "street" -> position.copy(streetNumber = name)
                else -> position
            }
            if (a.size > 0) {
                builder.setItems(a) { d, p ->
                    query.keywords = a[p]
                    manualQuery(districtSearch, query, builder)
                }.show()
            }else{
                builder.show().dismiss()
                binding.editLayout.hint = position.toString()
            }
        }
        districtSearch.searchDistrictAsyn()
    }
}
