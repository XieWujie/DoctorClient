package com.example.administrator.doctorClient.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.administrator.doctorClient.data.doctor.Patient
import com.example.administrator.doctorClient.data.doctor.PatientDao
import com.example.administrator.doctorClient.data.evaluation.Evaluation
import com.example.administrator.doctorClient.data.evaluation.EvaluationDao
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.data.message.MessageDao
import com.example.administrator.doctorClient.data.order.Order
import com.example.administrator.doctorClient.data.order.OrderDao
import com.example.administrator.doctorClient.data.user.User
import com.example.administrator.doctorClient.data.user.UserDao
import com.example.administrator.doctorClient.utilities.DATABASE_NAME


@Database(entities = [Patient::class,User::class,Message::class,Order::class,Evaluation::class],version = 3,exportSchema = false)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getClinicDao():PatientDao

    abstract fun getUserDao():UserDao

    abstract fun getMessageDao():MessageDao

    abstract fun getOrder():OrderDao

    abstract fun getEvaluationDao():EvaluationDao

    companion object {

        @Volatile
        private var instance:AppDatabase? = null

        fun getInstance(context: Context):AppDatabase{
            return instance?: synchronized(this){
                instance?: buildInstance(context).also { instance = it }
            }
        }
        private fun buildInstance(context: Context):AppDatabase{
            return Room.databaseBuilder(context,AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object :RoomDatabase.Callback(){
                }).allowMainThreadQueries()
                .build()
        }
    }
}