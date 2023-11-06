package com.heira.app.domain.use_case.getRouteOneToOne

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.Geometry
import com.heira.app.domain.repository.OpenRouteRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRouteOneToOneUseCase @Inject constructor(
    private val repository: OpenRouteRepository
) {

    suspend operator fun invoke(start: String, end: String): Resource<Geometry> {
        return try {
            val result = repository.getRouteOneToOneDirection(start = start, end = end).features[0].geometry
            Resource.Success(data = result)
        } catch (e: HttpException) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error")
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}