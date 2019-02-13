package com.example.administrator.doctorClient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.ActivityEditListBinding
import com.example.administrator.doctorClient.presenter.EditListPresenter

class EditListActivity : BaseActivity(){

    private lateinit var binding:ActivityEditListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_list)
        binding.presenter = EditListPresenter()
        setActionBar(binding.toolbar)
        setTitle("")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.user = UserManage.user
    }
}
