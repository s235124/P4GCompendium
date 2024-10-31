package com.example.p4g.HTTP

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://2o3wq.wiremockapi.cloud"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val api: PersonaApiService by lazy {
        retrofit.create(PersonaApiService::class.java)
    }
}