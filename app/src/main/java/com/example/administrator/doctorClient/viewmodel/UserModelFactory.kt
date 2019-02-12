package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.doctorClient.data.user.UserRepository


class UserModelFactory(private val userRepository: UserRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserModel(userRepository) as T
    }
}