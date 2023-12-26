package dev.archie.hse_android_lab3.api.repository

import dev.archie.hse_android_lab3.api.Resource
import dev.archie.hse_android_lab3.api.client.FeedClient
import dev.archie.hse_android_lab3.api.dto.FeedDto
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val feedClient: FeedClient
) {

    suspend fun getFeeds(): Resource<Array<FeedDto>> {
        return try {
            Resource.Success(feedClient.feeds())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occured.")
        }
    }

}