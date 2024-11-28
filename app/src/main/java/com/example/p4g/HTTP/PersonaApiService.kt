package com.example.p4g.HTTP

import com.example.p4g.model.Entity
import retrofit2.http.GET

interface PersonaApiService {
    @GET("/personas")
    suspend fun getPersonas(): Map<String, Entity>
}