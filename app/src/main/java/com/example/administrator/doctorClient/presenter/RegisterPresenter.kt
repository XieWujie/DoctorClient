package com.example.administrator.doctorClient.presenter

import android.app.Dialog
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.navigation.findNavController
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.MainActivity
import com.google.android.material.snackbar.Snackbar

data class RegisterPresenter(
    @Bindable var userName:String = "",
    @Bindable var firstPassword:String = "",
    @Bindable var secondPassword:String = "",
    @Bindable var mailBox:String = ""
): BaseObservable(){

    private var progressDialog: Dialog? = null

    fun register(view: View){
        if (checkMailBox(view)){
            if (progressDialog == null){
                progressDialog = Util.createProgressDialog(view.context)
            }
            progressDialog?.show()
            view.isClickable = false
            UserManage.register(view,userName,firstPassword,mailBox){
                progressDialog?.dismiss()
                view.isClickable = true
                if (it != null){
                    Util.toActivity<MainActivity>(view.context)
                }
            }
        }
    }

    private fun firstLogin(view: View){

    }

    private fun checkPasswordSame(view: View):Boolean{
        if (firstPassword != secondPassword){
            Snackbar.make(view,"两次密码不一样", Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun checkMailBox(view: View):Boolean{

        return true
    }

    fun onBackPress(view: View){
        view.findNavController().navigateUp()
    }
}