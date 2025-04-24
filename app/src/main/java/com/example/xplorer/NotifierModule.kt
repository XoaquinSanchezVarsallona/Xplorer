package com.example.xplorer

import com.example.xplorer.api.Notifier
import com.example.xplorer.api.ToastNotifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotifierModule {

    @Provides
    @Singleton
    fun provideNotifier(): Notifier {
        return ToastNotifier()
    }
}