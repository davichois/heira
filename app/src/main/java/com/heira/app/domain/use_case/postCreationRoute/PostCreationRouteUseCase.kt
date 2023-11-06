package com.heira.app.domain.use_case.postCreationRoute

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.RouteCreationDTO
import com.heira.app.data.remote.dto.toRouteModel
import com.heira.app.domain.model.RouteModel
import com.heira.app.domain.repository.CoreRepository
import java.io.IOException
import javax.inject.Inject

class PostCreationRouteUseCase @Inject constructor(
    private val repository: CoreRepository
) {

    suspend operator fun invoke(route: RouteCreationDTO): Resource<RouteModel> {
        return try {
            val result = repository.postCreationRoute(route = route).toRouteModel()
            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}