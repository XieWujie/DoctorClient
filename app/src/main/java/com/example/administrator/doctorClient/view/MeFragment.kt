package com.example.administrator.doctorClient.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.FragmentMeBinding
import com.example.administrator.doctorClient.presenter.MePresenter
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
        binding.presenter = MePresenter()
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        activity?.title = "我的"
        val user = UserManage.user
        if (user != null) {
            binding.user = user
            binding.isProve.text = if (!user.doctorCertification) "未证明" else "已证明"
        }
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
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Activity.RESULT_OK == resultCode) {
            if (data?.data!=null){
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
            UserManage.setAvatar(requireContext(),realPath) {
                if (it == null){
                    Glide.with(this@MeFragment).load(realPath).into(binding.avatar)
                    Util.log(binding.root,"头像更新成功")
                }else{
                    Util.log(binding.root,it.message)
                }
            }
        }
    }
}