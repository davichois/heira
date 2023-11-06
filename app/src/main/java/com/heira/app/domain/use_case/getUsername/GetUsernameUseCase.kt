package com.heira.app.domain.use_case.getUsername

import com.heira.app.core.Resource
import com.heira.app.data.remote.dto.toUserModel
import com.heira.app.domain.model.UserModel
import com.heira.app.domain.repository.CoreRepository
import java.io.IOException
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(
    private val repository: CoreRepository
) {

    suspend operator fun invoke(username: String): Resource<UserModel> {
        return try {
            val result = repository.getUsername(username = username).toUserModel()
            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server")
        }
    }

}