package com.example.administrator.doctorClient.adapter

import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import com.example.administrator.doctorClient.core.MessageManage
import com.example.administrator.doctorClient.data.cache.LocalCacheUtil
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.databinding.RightVoiceLayoutBinding
import com.example.administrator.doctorClient.utilities.PathUtil
import com.example.administrator.doctorClient.utilities.SENDING
import com.example.administrator.doctorClient.utilities.SEND_FAIL
import com.example.administrator.doctorClient.utilities.SEND_SUCCEED
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class RightVoiceHolder(val bind:RightVoiceLayoutBinding):BaseHolder(bind.root){

    private val mediaPlayer = MediaPlayer()

    override fun bind(any: Any) {
        if (any is Message){
            when(any.sendState){
                SENDING->{
                    bind.chatRightProgressbar.visibility = View.VISIBLE
                    bind.chatRightTvError.visibility = View.GONE
                }
                SEND_FAIL->{
                    bind.chatRightProgressbar.visibility = View.GONE
                    bind.chatRightTvError.visibility = View.VISIBLE
                    bind.chatRightTvError.setOnClickListener {
                        resendEvent(any)
                        it.visibility = View.GONE
                        bind.chatRightProgressbar.visibility = View.VISIBLE
                    }
                }
                SEND_SUCCEED->{
                    bind.chatRightProgressbar.visibility = View.GONE
                    bind.chatRightTvError.visibility = View.GONE
                }
            }
            bind.message = any
            bind.chatRightLayoutContent.setOnClickListener {
                playVoice(any)
            }
        }
    }

    private fun playVoice(message: Message){
        if (message.message.contains("http")){
           val  path = PathUtil.getAudioCachePath(bind.root.context,message.id)
            if (path == null){
                begin(message.message)
                return
            }
            LocalCacheUtil.downloadFile(message.message!!,path!!,false,object : LocalCacheUtil.DownLoadCallback(){
                override fun done(e: Exception?) {
                    if (e == null){
                        begin(path!!)
                    }else{
                        e.printStackTrace()
                        Snackbar.make(bind.root,"语音加载失败",Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }else{
            begin(message.message)
        }
    }

    private fun resendEvent(message: Message){
        MessageManage.sendMessage(message){

        }
    }

    private fun begin(path:String){
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }else{
                mediaPlayer.reset()
                mediaPlayer.setDataSource(bind.root.context, Uri.parse(path))
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
    }
}

