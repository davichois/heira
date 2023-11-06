package com.heira.app.domain.model

import com.heira.app.data.remote.dto.LogLatDTO

data class RouteModel(
    val airquality: Double,
    val end: LogLatDTO,
    val forestation: Double,
    val id: Int,
    val likes: Int,
    val noise: Double,
    val start: LogLatDTO,
    val tag: String
)
