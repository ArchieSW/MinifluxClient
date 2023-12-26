package dev.archie.hse_android_lab3.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.archie.hse_android_lab3.api.Resource
import dev.archie.hse_android_lab3.api.repository.UserRepository
import dev.archie.hse_android_lab3.model.CurrentUserState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(CurrentUserState())
        private set

    fun loadCurrentUserInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            when (val result = userRepository.getMe()) {
                is Resource.Success -> {
                    state = state.copy(
                        userDto = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        userDto = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }


}