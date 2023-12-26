package dev.archie.hse_android_lab3.model

import dev.archie.hse_android_lab3.api.dto.EntriesDto
import dev.archie.hse_android_lab3.api.dto.FeedDto

data class CurrentEntriesState(
    val entriesDto: EntriesDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)