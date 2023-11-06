package com.heira.app.data.remote.dto

import com.heira.app.domain.model.RouteFavoriteModel

data class RouteFavoriteDTO(
    val date: String,
    val id: Int,
    val route: RouteDTO,
    val user: UserRFDTO
)

fun RouteFavoriteDTO.toRouteFavoriteModel(): RouteFavoriteModel {
    return RouteFavoriteModel(
        date, id, route.toRouteModel(), user.toUserRFModel()
    )
}