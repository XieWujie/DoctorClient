package com.example.administrator.doctorClient.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.doctorClient.databinding.FragmentMeBinding
import com.example.administrator.doctorClient.presenter.MePresenter
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.utilities.Util


class MeFragment:Fragment(){

    private lateinit var binding:FragmentMeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMeBinding.inflate(inflater,container,false)
        binding.avatar.setOnClickListener {
            if (UserManage.user == null){
                Util.log(binding.root,"请先登陆")
            }else{
                requestPermission()
            }
        }
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        activity?.setTitle("我的")
        val user = UserManage.user
        binding.presenter = MePresenter(user?.avatar,user?.name?:"登陆")
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        } else {
            dispatchPictureIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                1 ->dispatchPictureIntent()
            }
        }else {
            Util.log(binding.root, "拒绝权限将不能正常使用该功能")
        }
    }



    private fun dispatchPictureIntent() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK, null)
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(photoPickerIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (Activity.RESULT_OK == resultCode) {
            if (data.data!=null){
                initAvatar(data.data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initAvatar(uri: Uri){
        val realPath = Util.getRealPathFromURI(requireContext(),uri)
        Glide.with(this).load(realPath).into(binding.avatar)
        if (realPath == null){
            Util.log(binding.root,"获取图片失败")
        }else {
            UserManage.setAvatar(realPath) {
                if (it == null){
                    Util.log(binding.root,"头像更新成功")
                }else{
                    Util.log(binding.root,it.message)
                }
            }
        }
    }
}