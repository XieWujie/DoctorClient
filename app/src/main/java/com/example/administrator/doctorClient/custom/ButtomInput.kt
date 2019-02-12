package com.example.administrator.doctorClient.custom

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.databinding.ButtomInputLayoutBinding
import com.example.administrator.doctorClient.utilities.TEXT_MESSAGE
import com.example.administrator.doctorClient.utilities.Util


class ButtomInput: LinearLayout {

    private lateinit var binding:ButtomInputLayoutBinding
    private var conversationId:String? = null
    private var conversationName:String? = null
    private var listener:BottomInputListener? = null
    constructor(context: Context):super(context){
        init(context)
    }

    fun init(conversationId:String,conversationName:String){
        this.conversationId = conversationId
        this.conversationName = conversationName
        binding.recordText.setConversation(conversationId,conversationName)
    }

    constructor(context: Context, attributeSet: AttributeSet):super(context,attributeSet){
        init(context)
    }

    private fun init(context: Context){
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.buttom_input_layout,this,true)
        send()
        sendImage()
        inputEvent()
        leftStateEvent()
    }

    fun setBottomInputListener(listener: BottomInputListener){
        if (this.listener == null){
            this.listener = listener
        }
    }


    private fun leftStateEvent(){
        binding.leftState.setOnClickListener {
            if (it.isSelected){
                it.isSelected = false
                binding.recordText.visibility = View.GONE
            }else{
                it.isSelected = true
                binding.recordText.visibility = View.VISIBLE

            }
        }
    }


    private fun send(){
        binding.rightState.setOnClickListener {
            if (it.isSelected) {
                val text = binding.centerText.text.toString()
                if (!TextUtils.isEmpty(text)) {
                    sendText(text)
                    binding.centerText.setText("")
                }
            }else{
                Util.hideSoftInput(it.context,it)
                if (binding.moreItem.visibility == View.VISIBLE){
                    binding.moreItem.visibility = View.GONE
                }else{
                    binding.moreItem.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun inputEvent(){
        binding.centerText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank()){
                    binding.rightState.isSelected = false
                }else{
                    binding.rightState.isSelected = true
                }
            }

        })
    }

    fun sendImage(){
        binding.image.setOnClickListener {
            listener?.onClick(TYPE_IMAGE)
        }
    }



    private fun sendText(content:String){
        check()
        MessageManage.sendMessage(conversationName!!,conversationId!!, TEXT_MESSAGE,content){

        }
    }

    private fun check(){
        if (conversationId == null){
            throw Throwable("the client id is not found")
        }

    }

    interface BottomInputListener{

        fun onClick(type:Int)
    }

    companion object {
        const val TYPE_IMAGE = 1
    }
}