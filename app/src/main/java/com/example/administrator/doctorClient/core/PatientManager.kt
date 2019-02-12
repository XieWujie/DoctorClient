package com.example.administrator.doctorClient.core

import android.content.Context
import com.avos.avoscloud.*
import com.example.administrator.doctorClient.data.AppDatabase
import com.example.administrator.doctorClient.data.doctor.Patient
import com.example.administrator.doctorClient.data.doctor.PatientRepository
import com.example.administrator.doctorClient.utilities.AVATAR

object PatientManager{

    private val patientMap = HashMap<String,Patient>()
    private var repository :PatientRepository? = null

    fun findPatientById(context: Context, doctorId:String, findCallback:(patient:Patient?)->Unit){
        initRepository(context)
        if (patientMap.containsKey(doctorId)){
            findCallback(patientMap[doctorId])
        }else{
            findPatientByNet(doctorId,findCallback)
        }
    }

    private fun findPatientByNet(patientId:String, findCallback:(patient:Patient?)->Unit){
        val o = AVObject.createWithoutData("_User",patientId)
        o.fetchInBackground(object :GetCallback<AVObject>(){
            override fun done(o: AVObject?, e: AVException?) {
                if (e == null){
                    with(o!!){
                        val name:String = getString("username")
                        val avatar = getString(AVATAR)
                        val patient = Patient(objectId,name,avatar)
                        repository?.addPatient(patient)
                        patientMap[patientId] = patient
                        findCallback(patient)
                    }
                }else{
                    findCallback(null)
                }
            }
        })
    }

    fun update(context: Context, patient: Patient){
        initRepository(context)
        repository?.addPatient(patient)
    }

    private fun initRepository(context: Context){
        if (repository == null)
        repository = PatientRepository.getInstance(AppDatabase.getInstance(context).getClinicDao())
    }
}