package dev.archie.hse_android_lab3.api.client

import dev.archie.hse_android_lab3.api.dto.UserDto
import retrofit2.http.GET

interface UserClient {

    @GET("v1/me")
    suspend fun me() : UserDto

}