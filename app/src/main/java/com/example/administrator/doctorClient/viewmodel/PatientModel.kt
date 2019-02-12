package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.administrator.doctorClient.data.doctor.PatientRepository

class PatientModel internal constructor(private val repository: PatientRepository):ViewModel(){

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(40)
        .build()

}