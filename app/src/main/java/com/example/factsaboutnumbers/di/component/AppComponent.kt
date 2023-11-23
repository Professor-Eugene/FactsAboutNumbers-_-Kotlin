package com.example.factsaboutnumbers.di.component

import android.app.Application
import com.example.factsaboutnumbers.base.BaseApplication
import com.example.factsaboutnumbers.di.module.AppModule
import com.example.factsaboutnumbers.di.module.MainActivityModule
import com.example.factsaboutnumbers.di.module.ViewModelModule
import com.example.factsaboutnumbers.di.util.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@AppScope
@Component(modules = [AppModule::class,
    ViewModelModule::class,
    MainActivityModule::class,
    AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}