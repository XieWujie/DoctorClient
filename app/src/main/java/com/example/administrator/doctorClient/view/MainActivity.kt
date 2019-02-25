package com.example.administrator.doctorClient.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.databinding.ActivityMainBinding
import com.example.administrator.doctorClient.utilities.Util

class MainActivity : BaseActivity(){

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.bottomLayout.setupWithNavController(findNavController(R.id.main_fragment))
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)
        val color = resources.getColor(R.color.blue_toolbar)
        Util.setStatusBar(this,color)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            val intent =  Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }else {
            return super.onKeyDown(keyCode, event)
        }
    }
}
