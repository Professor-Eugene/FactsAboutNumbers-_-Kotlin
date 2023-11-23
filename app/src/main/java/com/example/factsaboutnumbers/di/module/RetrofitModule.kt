package com.example.factsaboutnumbers.di.module

import com.example.factsaboutnumbers.data.rest.NumbersApiService
import com.example.factsaboutnumbers.di.util.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class RetrofitModule {

    private val BASE_URL = "http://numbersapi.com/"

    @AppScope
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @AppScope
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): NumbersApiService {
        return retrofit.create(NumbersApiService::class.java)
    }
}
