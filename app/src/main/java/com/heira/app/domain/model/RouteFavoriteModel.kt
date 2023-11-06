package com.heira.app.domain.model

data class RouteFavoriteModel(
    val date: String,
    val id: Int,
    val route: RouteModel,
    val user: UserRFModel
)