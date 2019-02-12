package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.doctorClient.data.evaluation.EvaluationRepository


class EvaluationFactory internal constructor(private  val repository: EvaluationRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EvaluationModel(repository)as T
    }
}