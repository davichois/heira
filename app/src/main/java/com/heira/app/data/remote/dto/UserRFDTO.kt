package com.heira.app.data.remote.dto

import com.heira.app.domain.model.UserRFModel

data class UserRFDTO(
    val id: Int,
    val username: String
)

fun UserRFDTO.toUserRFModel(): UserRFModel {
    return UserRFModel(
        id, username
    )
}