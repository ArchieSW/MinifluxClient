package dev.archie.hse_android_lab3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Entries : ScreenItem("home", Icons.Default.Home, "Home")
    object Feeds : ScreenItem("feeds", Icons.Default.List, "Feeds")
    object Settings : ScreenItem("settings", Icons.Default.Settings, "Settings")

    companion object {
        fun values(): Array<ScreenItem> {
            return arrayOf(ScreenItem.Entries, ScreenItem.Feeds, ScreenItem.Settings)
        }
    }
}
