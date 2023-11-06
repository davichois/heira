package com.heira.app.domain.use_case.getRoutesLikedUser

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.toRouteFavoriteModel
import com.heira.app.domain.model.RouteFavoriteModel
import com.heira.app.domain.repository.CoreRepository
import java.io.IOException
import javax.inject.Inject

class GetRoutesLikedUserUseCase @Inject constructor(
    private val repository: CoreRepository
) {

    suspend operator fun invoke(idUser: String): Resource<List<RouteFavoriteModel>> {
        return try {
            val result = repository.getRoutesLikedUser(idUser = idUser)
                .map { it -> it.toRouteFavoriteModel() }
            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}