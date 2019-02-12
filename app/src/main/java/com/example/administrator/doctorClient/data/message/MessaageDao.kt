package com.example.administrator.doctorClient.data.message

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MessageDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message)

    @Query("SELECT * FROM message WHERE conversationId = :id ORDER BY createAt")
    fun queryById(id:String): DataSource.Factory<Int,Message>

    @Query("SELECT * FROM message m WHERE owner = :owner AND createAt in (SELECT MAX(createAt) FROM message GROUP BY conversationId ) order BY createAt DESC")
    fun getNewMessage(owner:String): DataSource.Factory<Int,Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages:List<Message>)

    @Delete
    fun delete(message: Message)

    @Update
    fun update(message: Message)

    @Query("DELETE FROM message WHERE conversationId = :conversationId")
    fun delete(conversationId:String)
}