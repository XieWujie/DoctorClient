package com.example.administrator.doctorClient.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.databinding.ActivityStartBinding
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.os.Build
import android.view.View
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import androidx.annotation.Nullable
import androidx.navigation.findNavController


class StartActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        DataBindingUtil.setContentView<ActivityStartBinding>(this,R.layout.activity_start)
        navController = findNavController(R.id.fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
