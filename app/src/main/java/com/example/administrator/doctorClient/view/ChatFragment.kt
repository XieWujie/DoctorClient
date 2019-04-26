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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avos.avoscloud.im.v2.AVIMConversation
import com.example.administrator.doctorClient.adapter.ChatAdapter
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.custom.ButtomInput
import com.example.administrator.doctorClient.databinding.FragmentChatBinding
import com.example.administrator.doctorClient.utilities.IMAGE_MESSAGE
import com.example.administrator.doctorClient.utilities.Util
import com.example.administrator.doctorClient.utilities.ViewModelFactory
import com.example.administrator.doctorClient.viewmodel.MessageModel
import com.google.android.material.snackbar.Snackbar

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    private lateinit var model: MessageModel
    private val adapter = ChatAdapter()
    private var conversationName: String? = null
    private var conversationId: String? = null
    private var conversation: AVIMConversation? = null
    private var isInited = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        val factory = ViewModelFactory.getMessageModelFactory(requireContext())
        model = ViewModelProviders.of(this, factory).get(MessageModel::class.java)
        binding.setLifecycleOwner(this)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.chatRcView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRcView.adapter = adapter
    }

    fun begin(conversationName: String, conversation: AVIMConversation) {
        this.conversationId = conversation.conversationId
        this.conversationName = conversationName
        this.conversation = conversation
        conversation.read()
        binding.chatBottom.init(conversationId!!, conversationName)
        binding.chatBottom.setBottomInputListener(object : ButtomInput.BottomInputListener {
            override fun onClick(type: Int) {
                dispatchEvent(type)
            }
        })
        model.getMessage(conversationId!!).observe(this, Observer {
            adapter.submitList(it)
            if (!isInited && it.size < 10 && conversationId != null) {
                MessageManage.queryMessageByConversationId(conversationId!!, 20)
                isInited = true
            }

        })
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.chatRcView.scrollToPosition(adapter.itemCount - 1)
                conversation.read()
            }
        })
        binding.freshLayout.setOnRefreshListener {
            val message = if (adapter.currentList?.size ?: 0 > 0) {
                adapter.currentList?.get(0)
            } else {
                null
            }
            if (message != null) {
                MessageManage.queryMessageByTime(message.id, message.createAt)
            } else {
                MessageManage.queryMessageByConversationId(conversationId!!, 20)
            }
            binding.freshLayout.isRefreshing = false
        }
    }

    fun dispatchEvent(type: Int) {
        when (type) {
            ButtomInput.TYPE_IMAGE -> requestPermission(type)
        }
    }

    private fun requestPermission(type: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), type
            )
        } else {
            when (type) {
                ButtomInput.TYPE_IMAGE -> dispatchPictureIntent()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                ButtomInput.TYPE_IMAGE -> dispatchPictureIntent()
            }
        } else {
            Snackbar.make(binding.root, "拒绝权限将不能正常使用该功能", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun sendImageMessage(uri: Uri?) {
        if (uri != null) {
            val realPath = Util.getRealPathFromURI(requireContext(), uri)
            if (realPath != null) {
                MessageManage.sendMessage(conversationName!!, conversationId!!, IMAGE_MESSAGE, realPath) {

                }
            }
        }
    }

    private fun dispatchPictureIntent() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK, null)
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(photoPickerIntent, ButtomInput.TYPE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                ButtomInput.TYPE_IMAGE -> sendImageMessage(data.data)
                else -> {

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}