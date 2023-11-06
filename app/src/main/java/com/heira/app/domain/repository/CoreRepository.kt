package com.heira.app.domain.repository

import com.heira.app.data.remote.dto.RouteCreationDTO
import com.heira.app.data.remote.dto.RouteDTO
import com.heira.app.data.remote.dto.RouteFavoriteDTO
import com.heira.app.data.remote.dto.UserDTO
import com.heira.app.data.remote.dto.UserPostCreation

interface CoreRepository {

    suspend fun getRouteByTag(tag: String): List<RouteDTO>

    suspend fun getRouteById(id: String): RouteDTO

    suspend fun getRoutesLikedUser(idUser: String): List<RouteFavoriteDTO>

    suspend fun getUsername(username: String): UserDTO

    suspend fun postLikeRouteByCodeUser(code: String): RouteFavoriteDTO

    suspend fun postCreationUser(user: UserPostCreation): UserDTO

    suspend fun postCreationRoute(route: RouteCreationDTO): RouteDTO

}