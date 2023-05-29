package com.example.contactrandom.dagger

import android.content.Context
import com.example.contactrandom.MasterApp
import com.example.contactrandom.utils.ImageManager
import com.example.contactrandom.utils.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Created by marlon on 28/5/23. **/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerContext(): Context{
        return MasterApp.getContext()
    }

    @Provides
    fun providerPermission(context: Context): PermissionManager{
        return PermissionManager(context)
    }

    @Provides
    fun providerImageManager(context: Context): ImageManager {
        return ImageManager(context)
    }

}