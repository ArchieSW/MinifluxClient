package dev.archie.hse_android_lab3.api.client

import dev.archie.hse_android_lab3.api.dto.EntriesDto
import dev.archie.hse_android_lab3.api.dto.EntryDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EntryClient {

    @GET("/v1/entries")
    suspend fun entries(
        @Query("status") status: String,
        @Query("direction") direction: String,
        @Query("limit") limit: Long
    ): EntriesDto

}