package com.example.administrator.doctorClient.core

import android.content.Context
import com.avos.avoscloud.*
import com.example.administrator.doctorClient.data.AppDatabase
import com.example.administrator.doctorClient.data.evaluation.Evaluation
import com.example.administrator.doctorClient.data.evaluation.EvaluationRepository
import com.example.administrator.doctorClient.utilities.AVATAR
import java.lang.Exception

object EvaluateManager{

    private var repository:EvaluationRepository? = null

    private fun initRepository(context: Context){
        if (repository == null)
        repository = EvaluationRepository.getInstance(AppDatabase.getInstance(context).getEvaluationDao())
    }

    fun findDoctorEvaluation(context: Context,doctorId:String,findCallback:(e:Exception?)->Unit){
        initRepository(context)
        val q = AVQuery<AVObject>("Evaluation")
        q.whereEqualTo("doctorId",doctorId)
        q.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null) {
                    list!!.forEach { o ->
                        with(o) {
                            val patientId = getString("patientId")
                            val score = getDouble("score").toFloat()
                            val avatar = getString(AVATAR)
                            val name = getString("name")
                            val content = getString("content")
                            val evaluation =
                                Evaluation(objectId, createdAt.time, patientId, doctorId, score, avatar, name, content)
                            repository?.insert(evaluation)
                        }
                    }
                    findCallback(null)
                }else{
                    findCallback(e)
                }
            }
        })
    }
}