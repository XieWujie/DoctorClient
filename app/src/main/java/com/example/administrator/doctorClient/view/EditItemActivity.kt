package com.example.administrator.doctorClient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.ActivityEditItemBinding
import com.example.administrator.doctorClient.utilities.USER_NAME
import com.example.administrator.doctorClient.utilities.Util

class EditItemActivity : BaseActivity() {

    private lateinit var binding:ActivityEditItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_item)
        setSupportActionBar(binding.toolbar)
        dispatchEvent()
    }

    private fun dispatchEvent(){
        binding.cancel.setOnClickListener {
            finish()
        }
        binding.save.setOnClickListener {
            val value = binding.editText.text.toString()
            val key = intent.getStringExtra("key")
            val user = UserManage.user!!
            val u = when (key) {
                "goodAt" -> user.copy(goodAt = value)
                "qualification"->user.copy(qualification = value)
                "phone"->user.copy(phone = value)
                "graduatedSchool"->user.copy(graduatedSchool = value)
                USER_NAME->user.copy(name = value)
                "workTime"->user.copy(workerTime = value)
                else->user
            }
            UserManage.update(this,key,value,u){e->
                if (e == null){
                    finish()
                }else{
                    Util.log(binding.root,e.message)
                }
            }
        }
    }
}
