package com.example.administrator.doctorClient.data.user

import androidx.lifecycle.LiveData
import com.example.administrator.doctorClient.utilities.runOnNewThread

class UserRepository private constructor(private val userDao: UserDao) {


    fun getLastUser(): LiveData<User>? {
        return userDao.getLastUser()
    }

    fun addUser(user: User) {
        runOnNewThread {
            userDao.addUser(user)
        }
    }

    fun updata(user: User) {
        runOnNewThread {
            userDao.updata(user)
        }
    }

    fun deleteUser(user: User) {
        runOnNewThread {
            userDao.deleteUser(user)
        }
    }

    fun getUserById(id: String) = userDao.getUserById(id)

    companion object {

        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao).also { instance = it }
            }
    }
}
