package com.heira.app.domain.use_case.getRouteByTag

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.toRouteModel
import com.heira.app.domain.model.RouteModel
import com.heira.app.domain.repository.CoreRepository
import java.io.IOException
import javax.inject.Inject

class GetRouteByTagUseCase @Inject constructor(
    private val repository: CoreRepository
) {

    suspend operator fun invoke(tag: String): Resource<List<RouteModel>> {
        return try {
            val result = repository.getRouteByTag(tag = tag)
                .map { it -> it.toRouteModel() }
            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}