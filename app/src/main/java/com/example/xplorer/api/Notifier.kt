package com.example.xplorer.api

import android.content.Context
import android.widget.Toast

interface Notifier {
    fun notify(message: String, context: Context)
}

class ToastNotifier : Notifier {
    override fun notify(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

class TestNotifier : Notifier {
    override fun notify(message: String, context: Context) {
        println("TestNotifier: $message")
    }
}