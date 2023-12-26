package dev.archie.hse_android_lab3.model

import dev.archie.hse_android_lab3.api.dto.FeedDto

data class CurrentFeedsState(
    val feedDtos: Array<FeedDto>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
