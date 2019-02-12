package com.example.administrator.doctorClient.data.doctor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface PatientDao{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPatient(list:List<Patient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPatient(patient: Patient)

}