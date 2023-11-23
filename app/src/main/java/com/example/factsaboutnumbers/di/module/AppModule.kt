package com.example.factsaboutnumbers.di.module

import android.app.Application
import android.content.Context
import com.example.factsaboutnumbers.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [RetrofitModule::class, RoomModule::class])
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector() : MainActivity
}