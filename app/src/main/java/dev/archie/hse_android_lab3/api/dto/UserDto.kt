package dev.archie.hse_android_lab3.api.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    var username: String,
    @SerializedName("is_admin")
    var isAdmin: Boolean,
    @SerializedName("theme")
    var theme: String,
    @SerializedName("language")
    var language: String,
    @SerializedName("timezone")
    var timezone: String,
    @SerializedName("entry_sorting_direction")
    var entrySortingDirection: String,
    @SerializedName("entry_sorting_order")
    var entrySortingOrder: String,
    @SerializedName("stylesheet")
    var stylesheet: String,
    @SerializedName("google_id")
    var googleId: String,
    @SerializedName("openid_connect_id")
    var openidConnectId: String,
    @SerializedName("entries_per_page")
    var entriesPerPage: Int,
    @SerializedName("keyboard_shortcuts")
    var keyboardShortcuts: Boolean,
    @SerializedName("show_reading_time")
    var showReadingTime: Boolean,
    @SerializedName("entry_swipe")
    var entrySwipe: Boolean,
    @SerializedName("gesture_nav")
    var gestureNav: String,
    @SerializedName("last_login_at")
    var lastLoginAt: String,
    @SerializedName("display_mode")
    var displayMode: String,
    @SerializedName("default_reading_speed")
    var defaultReadingSpeed: Int,
    @SerializedName("cjk_reading_speed")
    var cjkReadingSpeed: Int,
    @SerializedName("default_home_page")
    var defaultHomePage: String,
    @SerializedName("categories_sorting_order")
    var categoriesSortingOrder: String
)