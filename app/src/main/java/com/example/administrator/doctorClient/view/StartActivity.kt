package com.example.administrator.doctorClient.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityStartBinding>(this,R.layout.activity_start)
        navController = Navigation.findNavController(this,R.id.fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
