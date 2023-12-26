package dev.archie.hse_android_lab3.api.dto

import com.google.gson.annotations.SerializedName


data class EntryDto (

    @SerializedName("id"           ) var id          : Int?     = null,
    @SerializedName("user_id"      ) var userId      : Int?     = null,
    @SerializedName("feed_id"      ) var feedId      : Int?     = null,
    @SerializedName("title"        ) var title       : String?  = null,
    @SerializedName("url"          ) var url         : String?  = null,
    @SerializedName("comments_url" ) var commentsUrl : String?  = null,
    @SerializedName("author"       ) var author      : String?  = null,
    @SerializedName("content"      ) var content     : String?  = null,
    @SerializedName("hash"         ) var hash        : String?  = null,
    @SerializedName("published_at" ) var publishedAt : String?  = null,
    @SerializedName("created_at"   ) var createdAt   : String?  = null,
    @SerializedName("status"       ) var status      : String?  = null,
    @SerializedName("share_code"   ) var shareCode   : String?  = null,
    @SerializedName("starred"      ) var starred     : Boolean? = null,
    @SerializedName("reading_time" ) var readingTime : Int?     = null,
    @SerializedName("enclosures"   ) var enclosures  : String?  = null,
    @SerializedName("feed"         ) var feed        : FeedDto?  = FeedDto()

)