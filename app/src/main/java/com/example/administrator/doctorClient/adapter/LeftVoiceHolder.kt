package com.example.administrator.doctorClient.adapter

import android.media.MediaPlayer
import android.net.Uri
import com.example.administrator.doctorClient.data.cache.LocalCacheUtil
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.databinding.LeftLayoutVoiceBinding
import com.example.administrator.doctorClient.utilities.PathUtil
import com.example.administrator.doctorClient.utilities.runOnNewThread

class LeftVoiceHolder(val bind:LeftLayoutVoiceBinding):BaseHolder(bind.root){

    private val mediaPlayer = MediaPlayer()

    override fun bind(any: Any) {
        if (any is Message){
            bind.message = any
            bind.voiceText.setOnClickListener {
                playVoice(any)
            }
        }
    }

    private fun playVoice(message: Message){
        val path :String?
        if (message.message.contains("http")){
            path = PathUtil.getAudioCachePath(bind.root.context,message.id)
            if (path == null)return
            LocalCacheUtil.downloadFile(message.message,path,false,object : LocalCacheUtil.DownLoadCallback(){
                override fun done(e: Exception?) {
                    if (e == null){
                        begin(path)
                    }
                }
            })
        }else{
            begin(message.message)
        }
    }

    private fun begin(path:String){
        runOnNewThread {
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
}

