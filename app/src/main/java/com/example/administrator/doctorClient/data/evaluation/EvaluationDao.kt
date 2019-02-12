package com.example.administrator.doctorClient.data.evaluation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EvaluationDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(evaluation: Evaluation)

    @Query("SELECT * FROM evaluation WHERE doctorId = :doctorId ORDER BY createAt DESC")
    fun findDoctorEvaluation(doctorId:String):androidx.paging.DataSource.Factory<Int,Evaluation>

    @Query("SELECT AVG(score) FROM evaluation WHERE doctorId = :doctorId ")
    fun findScore(doctorId: String):LiveData<Float>
}