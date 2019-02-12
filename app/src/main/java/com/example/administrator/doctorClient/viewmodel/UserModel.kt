package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import com.example.administrator.doctorClient.data.user.UserRepository

class UserModel(
    private val userRepository: UserRepository
): ViewModel(){
    val lastUser = userRepository.getLastUser()
}