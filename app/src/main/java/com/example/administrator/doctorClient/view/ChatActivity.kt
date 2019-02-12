package com.example.administrator.doctorClient.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.databinding.ActivityChatBinding
import com.example.administrator.doctorClient.utilities.CONVERSATION_ID
import com.example.administrator.doctorClient.utilities.CONVERSATION__NAME
import com.example.administrator.doctorClient.utilities.Util

class ChatActivity : AppCompatActivity(){

    private lateinit var chatFragment: ChatFragment
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat)
        chatFragment = supportFragmentManager.findFragmentById(R.id.chat_fragment) as ChatFragment
        initView()
        initConversation()
    }

    private fun initConversation() {
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

    private fun initView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp)
        setTitle("消息")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->finish()
        }
        return true
    }
}

