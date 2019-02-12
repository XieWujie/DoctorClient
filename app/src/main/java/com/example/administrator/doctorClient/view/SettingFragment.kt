package com.example.administrator.doctorClient.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.doctorClient.databinding.FragmentSettingBinding
import com.example.administrator.doctorClient.presenter.SettingPresenter

class SettingFragment:Fragment(){

    private lateinit var binding:FragmentSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        binding.presenter = SettingPresenter()
        return binding.root
    }
}