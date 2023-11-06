package com.heira.app.domain.use_case.getRouteById

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.toRouteModel
import com.heira.app.domain.model.RouteModel
import com.heira.app.domain.repository.CoreRepository
import java.io.IOException
import javax.inject.Inject

class GetRouteByIdUseCase @Inject constructor(
    private val repository: CoreRepository
) {

    suspend operator fun invoke(id: String): Resource<RouteModel> {
        return try {
            val result = repository.getRouteById(id = id).toRouteModel()
            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}