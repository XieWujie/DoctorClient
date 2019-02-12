package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.doctorClient.data.doctor.PatientRepository

class PatientFactory(private val respository: PatientRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientModel(respository) as T
    }
}