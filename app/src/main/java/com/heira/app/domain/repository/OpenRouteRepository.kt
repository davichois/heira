package com.heira.app.domain.repository

import com.heira.app.data.remote.dto.OpenRouteAPIResponse

interface OpenRouteRepository {

    suspend fun getRouteOneToOneDirection(start: String, end: String): OpenRouteAPIResponse

}