package dev.archie.hse_android_lab3.api.repository

import dev.archie.hse_android_lab3.api.Resource
import dev.archie.hse_android_lab3.api.client.EntryClient
import dev.archie.hse_android_lab3.api.dto.EntriesDto
import javax.inject.Inject

class EntryRepository @Inject constructor(
    private val entryClient: EntryClient
) {

    suspend fun getEntries(): Resource<EntriesDto> {
        return try {
            Resource.Success(entryClient.entries("unread", "desc", 20))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occured")
        }
    }
}