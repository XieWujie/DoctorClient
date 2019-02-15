package com.example.administrator.doctorClient.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.core.UserManage
import com.example.administrator.doctorClient.databinding.ActivityChatBinding
import com.example.administrator.doctorClient.utilities.CONVERSATION_ID
import com.example.administrator.doctorClient.utilities.CONVERSATION__NAME
import com.example.administrator.doctorClient.utilities.Util

class ChatActivity : BaseActivity(){

    private lateinit var chatFragment: ChatFragment
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat)
        setActionBar(binding.toolbar)
        chatFragment = supportFragmentManager.findFragmentById(R.id.chat_fragment) as ChatFragment
        initConversation()
    }

    private fun initConversation() {
        if (UserManage.user == null){
            AlertDialog.Builder(this)
                .setTitle("请先登陆")
                .setPositiveButton("前往登陆"){d,w->
                    Util.toActivity<StartActivity>(this)
                    finish()
                }
                .setNegativeButton("退出当前界面"){d,w->
                    finish()
                }.setCancelable(false)
                .show()
        }
        val conversationId = intent.getStringExtra(CONVERSATION_ID)
        val conversationName = intent.getStringExtra(CONVERSATION__NAME)
        MessageManage.findConversation(conversationId) { conversation ->
            if (conversation == null) {
                Util.log(binding.root, "获取会话失败")
                return@findConversation
            } else
                binding.centerText.text = conversationName
                 chatFragment.begin(conversationName, conversation)
        }
    }
}

