package com.example.healthanalyzers.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.healthanalyzers.R
import com.example.healthanalyzers.ui.login.LoginActivity


class NotificationTest : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val mIntent = Intent(context, LoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // 广播时发出通知弹窗，提醒用户进行今日的检测，点击之后进入软件
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, "normal")
            .setContentTitle("Health")
            .setContentText("今天进行健康检测了吗？")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        manager.notify(1, notification)
    }
}