package com.example.administrator.doctorClient.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.doctorClient.databinding.FragmentRegisterBinding
import com.example.administrator.doctorClient.presenter.RegisterPresenter

class RegisterFragment:Fragment(){

    private lateinit var binding:FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerhelper = RegisterPresenter()
        return binding.root
    }
}