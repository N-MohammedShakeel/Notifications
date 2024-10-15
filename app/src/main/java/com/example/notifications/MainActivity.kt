package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var ChannelID = "com.example.notifications.channel1"
    private var notificationManager: NotificationManager? = null
    private var Key = "reply_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createnotificationchannel(ChannelID, "DemoChannel", "this is a demo")

        binding.button.setOnClickListener {
            createnotification()
        }
    }

    private fun createnotification() {
        val home = Intent(this@MainActivity, HomeActivity::class.java)
        val pendinghome = PendingIntent.getActivity(
            this@MainActivity, 1, home, PendingIntent.FLAG_MUTABLE
        )

        val remoteinput: RemoteInput = RemoteInput.Builder(Key).run {
            setLabel("Type your name here")
            build()
        }
        val replyaction: NotificationCompat.Action = NotificationCompat.Action.Builder(0, "Reply", pendinghome)
            .addRemoteInput(remoteinput)
            .build()


        val details = Intent(this@MainActivity, DetailsActivity::class.java)
        val pendingdetails = PendingIntent.getActivity(this@MainActivity, 2, details, PendingIntent.FLAG_IMMUTABLE)
        val actiondetails = NotificationCompat.Action(0, "Details", pendingdetails)


        val settings = Intent(this@MainActivity, SettingsActivity::class.java)
        val pendingsettings = PendingIntent.getActivity(this@MainActivity, 3, settings, PendingIntent.FLAG_IMMUTABLE)
        val actionsettings = NotificationCompat.Action(0, "Settings", pendingsettings)


        val notificationid = 37
        val notification = NotificationCompat.Builder(this@MainActivity, ChannelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendinghome)
            .addAction(actiondetails)
            .addAction(actionsettings)
            .addAction(replyaction)
            .build()

        notificationManager?.notify(notificationid, notification)

        Log.d("MainActivity", "Notification Created")
    }

    private fun createnotificationchannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
