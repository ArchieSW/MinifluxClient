package dev.archie.hse_android_lab3.model

import dev.archie.hse_android_lab3.api.dto.UserDto

data class CurrentUserState(
    val userDto: UserDto? = null,
    val isLoading : Boolean = false,
    val error : String? = null
)
