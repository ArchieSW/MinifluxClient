package dev.archie.hse_android_lab3.api.client

import dev.archie.hse_android_lab3.api.dto.FeedDto
import retrofit2.http.GET

interface FeedClient {

   @GET("v1/feeds")
   suspend fun feeds() : Array<FeedDto>

}