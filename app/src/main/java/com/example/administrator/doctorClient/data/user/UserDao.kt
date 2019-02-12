package com.example.administrator.doctorClient.data.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updata(user: User)

    @Query("SELECT * FROM user ORDER BY loginTime DESC LIMIT -1,1")
    fun getLastUser(): LiveData<User>

    @Query("SELECT * FROM user WHERE id =:id")
    fun getUserById(id:String): LiveData<User>

    @Delete()
    fun deleteUser(user: User)

}