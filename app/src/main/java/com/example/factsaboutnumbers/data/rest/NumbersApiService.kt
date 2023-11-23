package com.example.factsaboutnumbers.data.rest

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApiService {
    @GET("{number}")
    suspend fun requestNewFact(@Path("number") number: Int): Response<String>
}