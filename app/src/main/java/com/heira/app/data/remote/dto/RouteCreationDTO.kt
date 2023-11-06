package com.heira.app.data.remote.dto

data class RouteCreationDTO(
    val airquality: Double,
    val end: LogLatDTO,
    val forestation: Double,
    val noise: Double,
    val start: LogLatDTO,
)
