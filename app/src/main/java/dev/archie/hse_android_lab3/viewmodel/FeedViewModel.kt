package dev.archie.hse_android_lab3.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.archie.hse_android_lab3.api.Resource
import dev.archie.hse_android_lab3.api.repository.FeedRepository
import dev.archie.hse_android_lab3.model.CurrentFeedsState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
): ViewModel() {

    var state by mutableStateOf(CurrentFeedsState())
        private set

    fun loadFeeds() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            when (val result = feedRepository.getFeeds()) {
                is Resource.Error -> {
                    state = state.copy(
                        feedDtos = null,
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        feedDtos = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

}