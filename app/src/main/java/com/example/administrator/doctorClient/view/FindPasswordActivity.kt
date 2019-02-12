package com.example.administrator.doctorClient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.databinding.ActivityFindPasswordBinding
import com.example.administrator.doctorClient.presenter.FindPasswordPresenter

class FindPasswordActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_find_password)
        setSupportActionBar(binding.findToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_36dp)
        binding.presenter = FindPasswordPresenter("")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }


}
