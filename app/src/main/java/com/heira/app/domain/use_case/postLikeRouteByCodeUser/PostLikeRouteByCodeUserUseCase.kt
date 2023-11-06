package com.heira.app.domain.use_case.postLikeRouteByCodeUser

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.toRouteFavoriteModel
import com.heira.app.domain.model.RouteFavoriteModel
import com.heira.app.domain.repository.CoreRepository
import java.io.IOException
import javax.inject.Inject

class PostLikeRouteByCodeUserUseCase @Inject constructor(
    private val repository: CoreRepository
) {

    suspend operator fun invoke(code: String): Resource<RouteFavoriteModel> {
        return try {
            val result = repository.postLikeRouteByCodeUser(code = code).toRouteFavoriteModel()
            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}