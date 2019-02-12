package com.example.administrator.doctorClient.utilities

import android.content.Context
import com.example.administrator.doctorClient.data.AppDatabase
import com.example.administrator.doctorClient.data.doctor.PatientRepository
import com.example.administrator.doctorClient.data.evaluation.EvaluationRepository
import com.example.administrator.doctorClient.data.message.MessageRepository
import com.example.administrator.doctorClient.data.order.OrderRepository
import com.example.administrator.doctorClient.data.user.UserRepository
import com.example.administrator.doctorClient.viewmodel.*

object ViewModelFactory{

    private fun getDatabase(context: Context) = AppDatabase.getInstance(context)

    fun getClinicModelFactory(context: Context) =
            PatientFactory(PatientRepository.getInstance(getDatabase(context).getClinicDao()))

    fun getUserModelFactory(context: Context) =
            UserModelFactory(UserRepository.getInstance(getDatabase(context).getUserDao()))

    fun getMessageModelFactory(context: Context) =
        MessageModelFactory(MessageRepository.getInstance(getDatabase(context).getMessageDao()))

    fun getOrderModelFactory(context: Context) =
            OrderModelFactory(OrderRepository.getInstance(getDatabase(context).getOrder()))

    fun getEvaluationFactory(context: Context) =
            EvaluationFactory(EvaluationRepository.getInstance(getDatabase(context).getEvaluationDao()))

}