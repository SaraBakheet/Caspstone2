package com.tuwaiq.caspstone2.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tuwaiq.caspstone2.R
import com.tuwaiq.caspstone2.home

class MyWorker(context: Context,workerParameters: WorkerParameters):
Worker(context,workerParameters){
    companion object{
        const val  CHANNEL_ID ="channel_id"
        const val NOTIFICATION_ID =1
    }


    override fun doWork(): Result {
        Log.d("do work succes","dowork:Success function called")

        showNotification()

        return Result.success()
    }
    private fun showNotification(){

       // implementation 'androidx.work:work-runtime-ktx:2.5.0'



        val intent =Intent(applicationContext, home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,0
        )

        val notification = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.chat1)
            .setContentTitle("CHAT APP")
            .setContentText("We are glad to have you join us ")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "channell Name"
            val channelDescription ="Channel Description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH
            val channel =NotificationChannel(CHANNEL_ID,channelName,channelImportance).apply {
                description= channelDescription
            }
            val notificationManager=applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            )
            as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            notify(NOTIFICATION_ID,notification.build())

        }



    }
}

