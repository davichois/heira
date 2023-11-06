package com.heira.app.data.remote

import com.heira.app.data.remote.dto.OpenRouteAPIResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenRouteApi {

    @GET("v2/directions/driving-car")
    suspend fun getRouteOneToOneDirection(@Query("api_key") api_key: String, @Query("start") start: String, @Query("end") end: String): OpenRouteAPIResponse

}