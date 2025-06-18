package com.example.xplorer.notifications

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import android.Manifest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppLifecycleObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : LifecycleObserver {
    private val notificationScheduler: NotificationScheduler = NotificationScheduler
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationScheduler.scheduleNotification(
                context = context,
                delayMillis = 3000L
            )
        }
    }
}