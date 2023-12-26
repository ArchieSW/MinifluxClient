package dev.archie.hse_android_lab3.api.dto

import com.google.gson.annotations.SerializedName

data class FeedDto (

    @SerializedName("id"                    ) var id                  : Int?      = null,
    @SerializedName("user_id"               ) var userId              : Int?      = null,
    @SerializedName("title"                 ) var title               : String?   = null,
    @SerializedName("site_url"              ) var siteUrl             : String?   = null,
    @SerializedName("feed_url"              ) var feedUrl             : String?   = null,
    @SerializedName("checked_at"            ) var checkedAt           : String?   = null,
    @SerializedName("etag_header"           ) var etagHeader          : String?   = null,
    @SerializedName("last_modified_header"  ) var lastModifiedHeader  : String?   = null,
    @SerializedName("parsing_error_message" ) var parsingErrorMessage : String?   = null,
    @SerializedName("parsing_error_count"   ) var parsingErrorCount   : Int?      = null,
    @SerializedName("scraper_rules"         ) var scraperRules        : String?   = null,
    @SerializedName("rewrite_rules"         ) var rewriteRules        : String?   = null,
    @SerializedName("crawler"               ) var crawler             : Boolean?  = null,
    @SerializedName("blocklist_rules"       ) var blocklistRules      : String?   = null,
    @SerializedName("keeplist_rules"        ) var keeplistRules       : String?   = null,
    @SerializedName("user_agent"            ) var userAgent           : String?   = null,
    @SerializedName("username"              ) var username            : String?   = null,
    @SerializedName("password"              ) var password            : String?   = null,
    @SerializedName("disabled"              ) var disabled            : Boolean?  = null,
    @SerializedName("ignore_http_cache"     ) var ignoreHttpCache     : Boolean?  = null,
    @SerializedName("fetch_via_proxy"       ) var fetchViaProxy       : Boolean?  = null,
    @SerializedName("category"              ) var category            : CategoryDto? = CategoryDto(),
    @SerializedName("icon"                  ) var icon                : IconDto?     = IconDto()

)

data class CategoryDto (

    @SerializedName("id"      ) var id     : Int?    = null,
    @SerializedName("user_id" ) var userId : Int?    = null,
    @SerializedName("title"   ) var title  : String? = null

)


data class IconDto (

    @SerializedName("feed_id" ) var feedId : Int? = null,
    @SerializedName("icon_id" ) var iconId : Int? = null

)