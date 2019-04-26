package com.example.administrator.doctorClient.viewmodel

import androidx.lifecycle.ViewModel
import com.example.administrator.doctorClient.data.user.UserRepository

class UserModel(
    userRepository: UserRepository
): ViewModel(){
    val lastUser = userRepository.getLastUser()
}