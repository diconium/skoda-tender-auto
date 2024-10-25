package com.skoda.launcher.service;


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skoda.launcher.R

val CHANNEL_ID = "skoda_notification"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i("TAG", "onMessageReceived: ")
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            // Process the data here
        }

        // Check if message contains a notification payload.

        var messageBody = ""
        var messageTitle = ""

        if (remoteMessage.notification != null) {
            messageBody = remoteMessage.notification!!.body!!
                    messageTitle = remoteMessage.notification!!.title!!
        } else if (remoteMessage.data != null) {
            messageBody = remoteMessage.data["message"]!!
            messageTitle =  remoteMessage.data["title"]!!
        } else
            return;

        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    /**
     * Creates a notification channel for Android 8.0+.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Home Fragment Channel"
            val descriptionText = "Channel for Home Fragment notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}