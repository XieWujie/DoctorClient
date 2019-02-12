package com.example.administrator.doctorClient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.adapter.CommentAdapter
import com.example.administrator.doctorClient.core.EvaluateManager
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.ActivityCommentBinding
import com.example.administrator.doctorClient.utilities.ViewModelFactory
import com.example.administrator.doctorClient.viewmodel.EvaluationModel

class CommentActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCommentBinding
    private lateinit var model:EvaluationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_comment)
        val factory = ViewModelFactory.getEvaluationFactory(this)
        model = ViewModelProviders.of(this,factory)[EvaluationModel::class.java]
        binding.setLifecycleOwner(this)
        EvaluateManager.findDoctorEvaluation(this,UserManage.user!!.userId){}
        initUI()
    }

    fun initUI(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CommentAdapter()
        recyclerView.adapter = adapter
        model.getDoctorEvaluation(UserManage.user!!.userId).observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
