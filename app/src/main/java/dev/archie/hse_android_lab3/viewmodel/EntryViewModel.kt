package dev.archie.hse_android_lab3.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.archie.hse_android_lab3.api.Resource
import dev.archie.hse_android_lab3.api.repository.EntryRepository
import dev.archie.hse_android_lab3.model.CurrentEntriesState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val entryRepository: EntryRepository
) : ViewModel() {

    var state by mutableStateOf(CurrentEntriesState())
        private set

    fun loadEntries() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            when (val result = entryRepository.getEntries()) {
                is Resource.Error -> {
                    state = state.copy(
                        entriesDto = null,
                        isLoading = false,
                        error = result.message
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        entriesDto = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

}