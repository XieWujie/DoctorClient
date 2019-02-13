package com.example.administrator.doctorClient.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.doctorClient.databinding.FragmentLoginBinding
import com.example.administrator.doctorClient.presenter.LoginPresenter
import com.example.administrator.doctorClient.utilities.USER_NAME

class LogInFragment:Fragment(){

    lateinit var binding:FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container,false)
        val userName = arguments?.getString(USER_NAME)
        val password = arguments?.getString("password")
        binding.loginhelper = LoginPresenter(userName?:"",password?:"")
        return binding.root
    }
}