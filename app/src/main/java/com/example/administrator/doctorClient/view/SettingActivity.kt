package com.example.administrator.doctorClient.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity(){

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySettingBinding>(this,R.layout.activity_setting)
        title = "设置"
       setActionBar(binding.toolbar)
        navController = findNavController(R.id.setting_nav)
    }
}

