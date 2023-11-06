package com.heira.app.data.remote.dto

import com.heira.app.domain.model.UserModel

data class UserDTO(
    val id: Int,
    val username: String,
    val hiraId: String
)

fun UserDTO.toUserModel(): UserModel {
    return UserModel(
        id, username, hiraId
    )
}
