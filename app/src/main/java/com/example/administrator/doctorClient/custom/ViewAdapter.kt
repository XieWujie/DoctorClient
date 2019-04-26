package com.example.administrator.doctorClient.custom

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.administrator.doctorClient.R
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
        @BindingAdapter("orderTime")
        fun setOrderTime(view: TextView,timeStamp: Long){
            val d = Date(timeStamp)
            val f = SimpleDateFormat("MM月dd日 HH:mm")
            val s = f.format(d)
            view.text = s
        }


        const val a = 3600000
        private var lastTime = 0L
        @JvmStatic
        @BindingAdapter("time")
        fun setTime(view: TextView, timeStamp:Long){
            if (timeStamp - lastTime > a) {
                lastTime = timeStamp
                val d = Date(timeStamp)
                if (DateUtils.isToday(timeStamp)) {
                    val c = if (GregorianCalendar()[GregorianCalendar.AM_PM] == 0) "上午" else "下午"
                    val f = SimpleDateFormat("hh:mm:ss")
                    val s = f.format(d)
                    view.text = "$c $s"
                } else {
                    val f = SimpleDateFormat("MM-dd HH:mm")
                    val s = f.format(d)
                    view.text = s
                }
            }
        }

        @JvmStatic
        @BindingAdapter("message_list_time")
        fun setMessageListTime(view: TextView, timeStamp: Long){
            val d = Date(timeStamp)
            if (DateUtils.isToday(timeStamp)) {
                val c = if (GregorianCalendar()[GregorianCalendar.AM_PM] == 0) "上午" else "下午"
                val f = SimpleDateFormat("hh:mm:ss")
                val s = f.format(d)
                view.text = "$c $s"
            } else {
                val f = SimpleDateFormat("MM-dd HH:mm")
                val s = f.format(d)
                view.text = s
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