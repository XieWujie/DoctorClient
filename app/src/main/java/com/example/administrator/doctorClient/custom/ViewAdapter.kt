package com.example.administrator.doctorClient.custom

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.amap.api.maps2d.AMapUtils
import com.amap.api.maps2d.model.LatLng
import com.bumptech.glide.Glide
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.data.Position
import java.text.SimpleDateFormat
import java.util.*

class ViewAdapter{
    companion object {

        @JvmStatic
        @BindingAdapter("imageSrc")
        fun setImage(view: ImageView, src:String?){
            if (src.isNullOrBlank()){
                view.setImageResource(R.drawable.doctor_default_avatar)
            }else {
                Glide.with(view)
                    .load(src)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("patient_image")
        fun setDoctorImage(view: ImageView, src:String?){
            if (src.isNullOrBlank()){
                view.setImageResource(R.drawable.patient_default_avatar)
            }else {
                Glide.with(view)
                    .load(src)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("setPraise")
        fun setPraise(view: TextView,content:Float){
            view.text = "${content.toString()} 分"
        }

        @JvmStatic
        @BindingAdapter("orderTime")
        fun setOrderTime(view: TextView,timeStamp: Long){
            val d = Date(timeStamp)
            val f = SimpleDateFormat("MM月dd日 HH:mm")
            val s = f.format(d)
            view.text = s
        }

        @JvmStatic
        @BindingAdapter("distance")
        fun setDistance(view: TextView,position: Position){
            val o = UserManage.position
            if (o == null)return
            val dPoint = LatLng(position.latitude,position.longitude)
            val mPoint = LatLng(o.latitude,o.longitude)
            val distance = AMapUtils.calculateLineDistance(dPoint,mPoint)
            if (distance>1000){
                view.text = "${(distance/100)/10f}千米"
            }else {
                view.text = "$distance 米"
            }
        }


        const val a = 8*3600000
        @JvmStatic
        @BindingAdapter("time")
        fun setTime(view: TextView, timeStamp:Long){
            val today = Date()
            val d = Date(timeStamp)
            val f = SimpleDateFormat("MM-dd HH:mm")
            val s = f.format(d)
            view.text = s
        }

        @JvmStatic
        @BindingAdapter("message_list_time")
        fun setMessageListTime(view: TextView, timeStamp: Long){
            val d = Date(timeStamp+ a)
            val f = SimpleDateFormat("dd日 HH:mm:ss")
            val s = f.format(d)
            view.text = s
        }

        @JvmStatic
        @BindingAdapter("unReadCount")
        fun setUnReadCount(view: TextView, count:Int){
            if (count>0) {
                view.text = count.toString()
            }
        }

        @JvmStatic
        @BindingAdapter("voiceTime")
        fun setVoiceTime(view: TextView, time:Double){
            val l = time.toString().split(".")
            if (l.size == 2){
                val t = """${l[0]}"${l[1].toInt()/100}"""
                view.text = t
            }else{
                view.text = time.toString()
            }
        }

    }
}