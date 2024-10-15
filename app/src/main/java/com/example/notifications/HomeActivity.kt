package com.example.notifications

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notifications.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recieveinput()
    }

    private fun recieveinput() {
        val KEY = "reply_key"
        val intent = this.intent
        val remoteinput = RemoteInput.getResultsFromIntent(intent)
        if (remoteinput != null) {
            val inputstring = remoteinput.getCharSequence(KEY).toString()
            var text = binding.textView.text.toString()
            text += inputstring
            binding.textView.text = text
        }


        var ChannelID = "com.example.notifications.channel1"
        val notificationid = 37

        val repliedNotification = NotificationCompat.Builder(this,ChannelID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentText("Your Reply Recieved")
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationid, repliedNotification)

    }
}