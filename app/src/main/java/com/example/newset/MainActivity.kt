package com.example.newset

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "Coder"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 检查手机版本是否支持通知；若支持则新增通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "DemoCode", NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }


        // 初始化界面控件与点击事件
        val btDefault: Button = findViewById(R.id.button_DefaultNotification)
        btDefault.setOnClickListener {
            // 点击 "系統預設通知" 按钮时触发通知
            sendDefaultNotification()
        }
    }

    // 点击 "系統預設通知" 时触发通知
    private fun sendDefaultNotification() {
        // 建立通知的内容
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_accessible_forward_24)
            .setContentTitle("哈囉你好！")
            .setContentText("跟你打個招呼啊～")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        // 发送通知
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(1, builder.build())
    }

    fun vibrateDevice(view: View?) {
        try {
            // 获取Vibrator实例
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            // 震动1000毫秒（1秒）
            vibrator.vibrate(1000)
        } catch (e: Exception) {
            // 打印异常信息
            e.printStackTrace()
        }
    }

    fun ringDevice(view: View?) {
        // 获取默认铃声的URI
        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        // 创建Ringtone对象并播放
        val ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneUri)
        ringtone.play()
    }

    // 按下按钮切换深色模式
    fun toggleDarkMode(view: View) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                // 如果当前模式是浅色模式，切换到深色模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                // 如果当前模式是深色模式，切换到浅色模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
                // 默认情况下，切换到系统设置的深色模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        recreate() // 重新创建 Activity 以应用新的模式
    }

    // 按下按钮打开字体大小设置页面
    fun openFontSizeSettings(view: View) {
        val intent = Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS)
        startActivity(intent)
    }
}

