package com.heira.app.data.remote.dto

import com.heira.app.domain.model.RouteModel

data class RouteDTO(
    val airquality: Double,
    val end: LogLatDTO,
    val forestation: Double,
    val id: Int,
    val likes: Int,
    val noise: Double,
    val start: LogLatDTO,
    val tag: String
)

fun RouteDTO.toRouteModel(): RouteModel {
    return RouteModel(
        airquality, end, forestation, id, likes, noise, start, tag
    )
}