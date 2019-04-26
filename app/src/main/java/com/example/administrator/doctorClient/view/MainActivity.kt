package com.example.administrator.doctorClient.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.databinding.ActivityMainBinding
import com.example.administrator.doctorClient.utilities.Util

class MainActivity : BaseActivity(){

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)
        val color = resources.getColor(R.color.blue_toolbar)
        Util.setStatusBar(this,color)
        val bottomLayout = binding.bottomLayout
        var lastId = 0
        val navController = findNavController(R.id.main_fragment)
        bottomLayout.setOnNavigationItemSelectedListener {item->
            if (lastId!=item.itemId){
                lastId = item.itemId
                navController.navigate(lastId)
            }
            true
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK){
            val intent =  Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            true
        }else {
            super.onKeyDown(keyCode, event)
        }
    }
}
