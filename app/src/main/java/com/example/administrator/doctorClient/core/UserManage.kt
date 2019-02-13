package com.example.administrator.doctorClient.core

import android.content.Context
import android.view.View
import com.avos.avoscloud.*
import com.avos.avoscloud.im.v2.AVIMClient
import com.avos.avoscloud.im.v2.AVIMException
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback
import com.example.administrator.doctorClient.data.AppDatabase
import com.example.administrator.doctorClient.data.Position
import com.example.administrator.doctorClient.data.user.User
import com.example.administrator.doctorClient.data.user.UserRepository
import com.example.administrator.doctorClient.utilities.AVATAR
import com.example.administrator.doctorClient.utilities.Util
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.Exception

object UserManage{

    var respository:UserRepository? = null
    var client:AVIMClient? = null
    var user:User? = null
    var position:Position? = null

    fun login(view: View, username:String,password:String,loginCallback:(user:User?)->Unit){
        initRespository(view.context)
        AVUser.logInInBackground(username,password,object : LogInCallback<AVUser>(){
            override fun done(a: AVUser?, e: AVException?) {
                if (e==null){
                    val  c = AVIMClient.getInstance(a!!.objectId)
                    c.open(object : AVIMClientCallback(){
                        override fun done(c: AVIMClient?, e: AVIMException?) {
                            if (e==null){
                                getUser(password,a!!){u->
                                    user = u
                                    respository?.addUser(u)
                                    loginCallback(user)
                                    return@getUser
                                }
                            }else{
                                Util.log(view,e.message)
                                loginCallback(null)
                            }
                        }
                    })

                }else{
                    Util.log(view,e?.message)
                    loginCallback(null)
                }
            }

        })
    }

    private fun getUser(password: String,o:AVObject,getCallback:(user:User)->Unit) {
        with(o) {
            val name: String = getString("username")
            val mailBox = getString("email")
            val avatar = getString("avatar")
            val qualification: String = getString("qualification") ?: "未知"
            val education: String = getString("education") ?: "未知"
            val doctorCertification: Boolean = getBoolean("doctorCertification") ?: false
            val authentication: Boolean = getBoolean("authentication") ?: false
            val workerTime: String = getString("workerTime") ?: "未知"
            val graduatedSchool: String = getString("graduatedSchool") ?: "未知"
            val workerAddress: String = getString("workerAddress") ?: "未知"
            val phone: String = getString("phone") ?: "未知"
            val historyOrderCount: Int = getInt("historyOrder") ?: 0
            val goodAt: String = getString("goodAt") ?: "未知"
            val country: String = getString("country") ?: ""
            val province: String = getString("province") ?: ""
            val city: String = getString("city") ?: "未知"
            val district: String = getString("district") ?: ""
            val streetNumber: String = getString("street") ?: ""
            val latitude: Double = getDouble("latitude")
            val longitude: Double = getDouble("longitude")
            val description = getString("description") ?: "未知"
            val position = Position(country, province, city, district, streetNumber, description, latitude, longitude)
            val user = User(
                objectId,
                name,
                password,
                updatedAt.time,
                mailBox,
                false,
                avatar,
                position,
                qualification,
                education,
                doctorCertification,
                authentication,
                workerTime,
                graduatedSchool,
                workerAddress,
                phone,
                historyOrderCount,
                goodAt
            )
            getCallback(user)
        }
    }

     fun register( view: View,username:String,password:String,mailBox:String,callback:(user:User?)->Unit) {
         initRespository(view.context)
        val user = AVUser()
        user.username = username
        user.setPassword(password)
        user.email = mailBox
        user.signUpInBackground(object : SignUpCallback(){
            override fun done(e: AVException?) {
                if (e!=null){
                    callback(null)
                    Util.log(view,e.message)

                }else{
                    val id = user.objectId
                    val  c = AVIMClient.getInstance(id)
                    c.open(object : AVIMClientCallback(){
                        override fun done(c: AVIMClient?, e: AVIMException?) {
                            if (e==null){
                                getUser(password,user){u->
                                    respository?.addUser(u)
                                    UserManage.user = u
                                    client = c
                                    callback(u)
                                    return@getUser
                                }
                            }else{
                                Util.log(view,e.message)
                                callback(null)
                            }
                        }
                    })

                }
            }

        })
    }



    private fun initRespository(context: Context){
        if (respository == null)
        respository = UserRepository.getInstance(AppDatabase.getInstance(context).getUserDao())
    }

    fun update(context: Context,key:String,value:String, u: User,callback:(e:Exception?)->Unit){
        initRespository(context)
        val o = AVObject.createWithoutData("_User",u.userId)
        o.put(key,value)
        o.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    respository?.addUser(u)
                    user = u
                    callback(null)
                }else{
                    callback(e)
                }
            }
        })
    }

    fun updatePosition(context: Context,position: Position,upDateCallback: (e: Exception?) -> Unit){
        initRespository(context)
        val o = AVObject.createWithoutData("_User", user!!.userId)
        with(o){
            put("country",position.country)
            put("province",position.province)
            put("district",position.district)
            put("city",position.city)
            put("street",position.streetNumber)
            put("longitude",position.longitude)
            put("latitude",position.latitude)
            put("description",position.description)
            saveInBackground(object :SaveCallback(){
                override fun done(e: AVException?) {
                    if (e == null){
                        user = user!!.copy(position = position)
                        respository?.addUser(user!!)
                        upDateCallback(null)
                    }else{
                        upDateCallback(e)
                    }
                }
            })
        }
    }

     fun setAvatar(context: Context,path: String,upDateCallback:(e:Exception?)->Unit) {
         initRespository(context)
        val avFile = AVFile.withAbsoluteLocalPath("${user!!.name}.jpg",path)
        avFile.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                    val o = AVObject.createWithoutData("_User", user!!.userId)
                    o.put(AVATAR,avFile.url)
                    o.saveInBackground()
                    val u = user!!.copy(avatar = avFile.url)
                    respository?.updata(u)
                    user = u
                    upDateCallback(null)
                }else{
                    upDateCallback(e)
                }
            }
        })
    }

    fun prove(context: Context,prove1:String,prove2:String,prove3:String,proveCallback:(e:Exception?)->Unit){
        initRespository(context)
        val c = CountDownLatch(3)
        val o = AVObject.createWithoutData("_User", user!!.userId)
        save("prove1",prove1,o,c)
        save("prove2",prove2,o,c)
        save("prove3",prove3,o,c)
        try {
            c.await(30,TimeUnit.SECONDS)
            o.put("doctorCertification",true)
            o.put("authentication",true)
            o.saveInBackground(object :SaveCallback(){
                override fun done(e: AVException?) {
                    if (e == null){
                        user = user!!.copy(authentication = true,doctorCertification = true)
                        respository?.addUser(user!!)
                    }
                    proveCallback(e)
                }
            })
        }catch (e:InterruptedException){
            proveCallback(e)
        }
    }

    private fun save(key: String,value:String, o: AVObject,c:CountDownLatch){
        val avFile = AVFile.withAbsoluteLocalPath("$key.jpg",value)
        avFile.saveInBackground(object :SaveCallback(){
            override fun done(e: AVException?) {
                if (e == null){
                   o.put(key,avFile.url)
                    c.countDown()
                }
            }
        })
    }

    fun logout(){
        if (user == null)return
        user = user!!.copy(isLogout = true)
        respository?.addUser(user!!)
        client?.close(null)
        client = null
    }
}