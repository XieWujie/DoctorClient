package com.example.administrator.doctorClient.utilities

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.view.ChatActivity
import java.text.SimpleDateFormat

object NotifyUtil{

    fun createNewMessageNotification(context: Context,message:Message){
        if (!isBackground(context)) {
            return
        }else {
            val content = when(message.type){
                IMAGE_MESSAGE->"图片"
                VOICE_MESSAGE->"语音"
                else->message.message
            }
            val remoteView = RemoteViews(context.packageName, R.layout.notify_remote)
            remoteView.setTextViewText(R.id.name_text, message.fromName)
            remoteView.setTextViewText(R.id.content_text, content)
            val f = SimpleDateFormat("HH:mm")
            val time = f.format(message.createAt)
            remoteView.setTextViewText(R.id.time_text, time)
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(CONVERSATION__NAME, message.conversationName)
            intent.putExtra(CONVERSATION_ID, message.conversationId)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notify_icon)
                .setContentTitle(message.message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContent(remoteView)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(10, builder.build())
        }
    }

    fun isBackground(context: Context):Boolean{
        val am =  context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.getRunningTasks(1)
        if (!tasks.isEmpty()) {
            val  topActivity = tasks[0].topActivity
            if (!(topActivity.packageName == context.packageName)) {
                return true;
            }
        }
        return false
    }
}