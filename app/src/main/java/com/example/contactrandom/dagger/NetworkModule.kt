package com.example.contactrandom.dagger

import com.example.contactrandom.repo.ApiContact
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
/** Created by marlon on 27/5/23. **/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun  provideApiContac(retrofit: Retrofit): ApiContact {
        return retrofit.create(ApiContact::class.java)
    }
}