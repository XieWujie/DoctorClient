package com.example.administrator.doctorClient.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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

class ProveActivity : BaseActivity() {

    private lateinit var binding:ActivityProveBinding
    private var image = -1
    private var prove1:String? = null
    private var prove2:String? = null
    private var prove3:String? = null

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
        binding.imageView4.setOnClickListener {
            image = 4
            requestPermission()
        }
        binding.cancel.setOnClickListener {
            finish()
        }
        binding.save.setOnClickListener {
            if (prove1!=null && prove2!=null && prove3!=null){
                val dialog = Util.createProgressDialog(this)
                dialog.show()
                it.isClickable = false
                UserManage.prove(this,prove1!!,prove2!!,prove3!!){e->
                    dialog.dismiss()
                    it.isClickable = true
                    if (e == null){
                        finish()
                    }else{
                        Util.log(binding.root, e.message)
                    }
                }
            }
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
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           dispatchPictureIntent()
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
                initAvatar(data.data!!)
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
                2->{
                    prove1 = realPath
                    Glide.with(this).load(realPath).into(binding.imageView2)
                }
                3->{
                    prove2 = realPath
                    Glide.with(this).load(realPath).into(binding.imageView3)
                }
                4->{
                    prove3 = realPath
                    Glide.with(this).load(realPath).into(binding.imageView4)
                }
            }
        }
    }
}
