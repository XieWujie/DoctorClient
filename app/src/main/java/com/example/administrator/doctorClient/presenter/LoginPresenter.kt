package com.example.administrator.doctorClient.presenter

import android.app.Dialog
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.view.FindPasswordActivity
import com.example.administrator.doctorClient.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class LoginPresenter(
    @Bindable var userName:String,
    @Bindable var password:String
): BaseObservable() {

    private var progressDialog: Dialog? = null
    var activity: WeakReference<FragmentActivity>? = null
    fun login(view: View) {
        if (userName.isBlank()) {
            Snackbar.make(view, "用户名不能为空", Snackbar.LENGTH_LONG).show()
            return
        }
        if (progressDialog == null) {
            progressDialog = Util.createProgressDialog(view.context)
        }
        progressDialog?.show()
        view.isClickable = false
        UserManage.login(view,userName,password){
            progressDialog?.dismiss()
            view.isClickable = true
            if (it != null){
                MessageManage.init(view.context,it)
                Util.toActivity<MainActivity>(view.context)
            }
        }

    }

    fun register(view: View) {
        view.findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
    }

    fun findPassword(view: View){
        Util.toActivity<FindPasswordActivity>(view.context)
    }

}