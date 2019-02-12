package com.example.administrator.doctorClient.data.doctor

import com.example.administrator.doctorClient.utilities.runOnNewThread

class PatientRepository private constructor(private val patientDao: PatientDao){


     fun addPatient(list: List<Patient>){
        runOnNewThread {
            patientDao.addPatient(list)
        }
    }

    fun addPatient(patient:Patient){
       runOnNewThread {
           patientDao.addPatient(patient)
       }
    }


    companion object {

        @Volatile private var instance: PatientRepository? = null

        fun getInstance(patientDao: PatientDao) =
            instance ?: synchronized(this) {
                instance ?: PatientRepository(patientDao).also { instance = it }
            }
    }
}