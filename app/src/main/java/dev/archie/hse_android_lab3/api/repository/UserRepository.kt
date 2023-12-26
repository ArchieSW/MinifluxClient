package dev.archie.hse_android_lab3.api.repository

import dev.archie.hse_android_lab3.api.Resource
import dev.archie.hse_android_lab3.api.client.UserClient
import dev.archie.hse_android_lab3.api.dto.UserDto
import javax.inject.Inject

class UserRepository @Inject constructor(private val userClient: UserClient) {

    suspend fun getMe(): Resource<UserDto> {
        return try {
            Resource.Success(userClient.me())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}