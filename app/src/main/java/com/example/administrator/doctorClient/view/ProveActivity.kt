package com.example.administrator.doctorClient.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.ActivityProveBinding
import com.example.administrator.doctorClient.utilities.Util

class ProveActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProveBinding
    private var image = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_prove)
        dispatchEvent()
    }

    private fun dispatchEvent(){
        binding.imageView2.setOnClickListener {
            image = 2
            requestPermission()
        }
        binding.imageView3.setOnClickListener {
            image = 3
            requestPermission()
        }
        binding.imageView3.setOnClickListener {
            image = 4
            requestPermission()
        }
        binding.cancel.setOnClickListener {
            finish()
        }
        binding.save.setOnClickListener {

        }
    }
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Activity.RESULT_OK == resultCode) {
            if (data?.data!=null){
                initAvatar(data.data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initAvatar(uri: Uri){
        val realPath = Util.getRealPathFromURI(this,uri)
        if (realPath == null){
            Util.log(binding.root,"获取图片失败")
        }else {
            when(image){
                2->Glide.with(this).load(realPath).into(binding.imageView2)
                3->Glide.with(this).load(realPath).into(binding.imageView3)
                4->Glide.with(this).load(realPath).into(binding.imageView4)
            }
        }
    }
}
