package com.example.administrator.doctorClient.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.administrator.doctorClient.R
import com.example.administrator.doctorClient.data.message.Message
import com.example.administrator.doctorClient.view.ChatActivity

object NotifyUtil{

    fun createNewMessageNotification(context: Context,message:Message){
        val remoteView = RemoteViews(context.packageName, R.layout.notify_remote)
        remoteView.setImageViewResource(R.id.message_avatar,R.drawable.ic_launcher_background)
        remoteView.setTextViewText(R.id.name_text,message.fromName)
        remoteView.setTextViewText(R.id.content_text,message.message)
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(CONVERSATION__NAME,message.conversationName)
        intent.putExtra(CONVERSATION_ID,message.conversationId)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(message.message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setContent(remoteView)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(10,builder.build())
    }
}