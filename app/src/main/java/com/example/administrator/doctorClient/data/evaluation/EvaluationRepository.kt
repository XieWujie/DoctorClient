package com.example.administrator.doctorClient.data.evaluation

import com.example.administrator.doctorClient.utilities.runOnNewThread

class EvaluationRepository private constructor(private val dao: EvaluationDao){

    fun insert(evaluation: Evaluation){
        runOnNewThread {
            dao.insert(evaluation)
        }
    }

    fun findDoctorEvaluation(doctorId:String) = dao.findDoctorEvaluation(doctorId)

    companion object {
        @Volatile
        private var instance: EvaluationRepository? = null
        fun getInstance(dao: EvaluationDao): EvaluationRepository =
            instance ?: synchronized(this) {
                instance ?: EvaluationRepository(dao).also { instance = it }
            }
    }
}