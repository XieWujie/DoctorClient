package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.administrator.doctorClient.data.evaluation.Evaluation
import com.example.administrator.doctorClient.data.evaluation.EvaluationRepository

class EvaluationModel(private val repository: EvaluationRepository):ViewModel(){

    private val config =  PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(20)
        .build()

     fun getDoctorEvaluation(doctorId:String) =
        LivePagedListBuilder<Int,Evaluation>(repository.findDoctorEvaluation(doctorId),config).build()

     fun getScore(doctorId: String) = repository.findScore(doctorId)
}