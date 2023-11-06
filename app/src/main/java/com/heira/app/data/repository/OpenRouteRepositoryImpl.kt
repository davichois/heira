package com.heira.app.data.repository

import com.heira.app.core.Constants
import com.heira.app.data.remote.OpenRouteApi
import com.heira.app.data.remote.dto.OpenRouteAPIResponse
import com.heira.app.domain.repository.OpenRouteRepository

class OpenRouteRepositoryImpl(
    private val api: OpenRouteApi
): OpenRouteRepository {

    override suspend fun getRouteOneToOneDirection(
        start: String,
        end: String
    ): OpenRouteAPIResponse {
        return api.getRouteOneToOneDirection(start = start, end = end, api_key = Constants.API_KEY)
    }

}