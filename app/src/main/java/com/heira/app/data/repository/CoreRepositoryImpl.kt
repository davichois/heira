package com.heira.app.data.repository

import com.heira.app.data.remote.CoreApi
import com.heira.app.data.remote.dto.RouteCreationDTO
import com.heira.app.data.remote.dto.RouteDTO
import com.heira.app.data.remote.dto.RouteFavoriteDTO
import com.heira.app.data.remote.dto.UserDTO
import com.heira.app.data.remote.dto.UserPostCreation
import com.heira.app.domain.repository.CoreRepository

class CoreRepositoryImpl(
    private val api: CoreApi
): CoreRepository {

    override suspend fun getRouteByTag(tag: String): List<RouteDTO> {
        return api.getRouteByTag(tag = tag).body
    }

    override suspend fun getRouteById(id: String): RouteDTO {
        return api.getRouteById(id = id).body
    }

    override suspend fun getRoutesLikedUser(idUser: String): List<RouteFavoriteDTO> {
        return api.getRoutesLikedUser(idUser = idUser).body
    }

    override suspend fun getUsername(username: String): UserDTO {
        return api.getUsername(username = username).body
    }

    override suspend fun postLikeRouteByCodeUser(code: String): RouteFavoriteDTO {
        return api.postLikeRouteByCodeUser(code = code).body
    }

    override suspend fun postCreationUser(user: UserPostCreation): UserDTO {
        return api.postCreationUser(user = user).body
    }

    override suspend fun postCreationRoute(route: RouteCreationDTO): RouteDTO {
        return api.postCreationRoute(route = route).body
    }

}