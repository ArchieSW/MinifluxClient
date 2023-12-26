package dev.archie.hse_android_lab3.api.dto

import com.google.gson.annotations.SerializedName


data class EntriesDto (

    @SerializedName("total"   ) var total   : Int?               = null,
    @SerializedName("entries" ) var entries : ArrayList<EntryDto> = arrayListOf()

)