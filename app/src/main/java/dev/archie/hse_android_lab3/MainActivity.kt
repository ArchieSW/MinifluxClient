package dev.archie.hse_android_lab3

import android.os.Bundle
import android.webkit.URLUtil
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import de.charlex.compose.material3.HtmlText
import dev.archie.hse_android_lab3.api.dto.EntryDto
import dev.archie.hse_android_lab3.api.dto.UserDto
import dev.archie.hse_android_lab3.model.CurrentUserState
import dev.archie.hse_android_lab3.ui.theme.Hseandroidlab3Theme
import dev.archie.hse_android_lab3.viewmodel.EntryViewModel
import dev.archie.hse_android_lab3.viewmodel.FeedViewModel
import dev.archie.hse_android_lab3.viewmodel.UserViewModel
import net.engawapg.lib.zoomable.ZoomState
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.jsoup.Jsoup
import java.net.URI
import java.time.Duration
import java.time.Period
import java.time.ZonedDateTime
import kotlin.math.min

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()
    private val entryViewModel: EntryViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.loadCurrentUserInfo()
        feedViewModel.loadFeeds()
        entryViewModel.loadEntries()

        setContent {
            Hseandroidlab3Theme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("MiniFlux")
                            },
                            actions = {
                                UserButton(
                                    state = CurrentUserState(
                                        userDto = userViewModel.state.userDto,
                                        isLoading = userViewModel.state.isLoading,
                                        error = userViewModel.state.error
                                    )
                                )
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            ScreenItem.values().forEach { item ->
                                NavigationBarItem(
                                    selected = navController.currentDestination?.route == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label
                                        )
                                    })
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        NavGraph(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun NavGraph(
        navController: NavHostController
    ) {
        NavHost(
            navController = navController,
            startDestination = ScreenItem.Entries.route
        ) {
            composable(ScreenItem.Feeds.route) {
                FeedsScreen(
                    feedViewModel = feedViewModel
                )
            }
            composable(ScreenItem.Entries.route) { EntriesScreen(entryViewModel, navController) }
            dialog(
                route = ScreenItem.Entries.route + "/{entryId}",
                arguments = listOf(navArgument("entryId") { type = NavType.IntType }),
                dialogProperties = DialogProperties(
                    true,
                    true,
                    SecureFlagPolicy.Inherit,
                    false,
                    true
                )
            ) { navBackStackEntry ->
                val entryId = navBackStackEntry.arguments!!.getInt("entryId")
                Entry(entryId, entryViewModel, navController)
            }
            composable(ScreenItem.Settings.route) { SettingsScreen() }
        }
    }

}

@Composable
fun SettingsScreen() {
    Text(text = "TODO Settings screen")
}

@Composable
fun Entry(entryId: Int, entryViewModel: EntryViewModel, navController: NavHostController) {
    val entry = entryViewModel.state.entriesDto?.entries?.find { it.id == entryId }
    Scaffold(
        floatingActionButton = {
            val uriHandler = LocalUriHandler.current
            entry?.url?.let {
                FloatingActionButton(onClick = { uriHandler.openUri(it) }) {
                    Icon(imageVector = Icons.Default.ArrowForward, null)
                }
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            entry?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.TopStart
                ) {
                    Row {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                    Column {
                        Column(
                            Modifier
                                .padding(horizontal = 15.dp, vertical = 10.dp)
                                .padding(start = 30.dp)
                        ) {
                            Text(
                                text = entry.title ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = TextUnit(6f, TextUnitType.Em)
                            )
                            ChipsForEntry(entry)
                        }
                        LazyColumn(
                            Modifier
                                .padding(horizontal = 15.dp, vertical = 10.dp)
                                .padding(start = 30.dp)
                        ) {
                            val htmlContent = entry.content ?: ""
                            val document = Jsoup.parse(htmlContent)
                            val elements = document.select("img")
                            val imageSources = elements.map { it.attr("src") }

                            val zoomStates = HashMap<String, ZoomState>()

                            items(imageSources) { imageUrl ->
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Something from entry",
                                    modifier = Modifier.zoomable(
                                        zoomStates.getOrPut(imageUrl, { rememberZoomState() })
                                    )
                                )
                            }

                            item {
                                HtmlText(text = htmlContent.replace("<img[^>]*>".toRegex(), ""))
                            }
//                    item {
//                        Text(text = "Plain text")
//                        Text(text = entry.content ?: "")
//                    }
//                Text(text = "Plain text")
//                Text(text = entry.content ?: "")
                        }


                    }
                }
            }

        }

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsForEntry(entry: EntryDto, fontSize: TextUnit = TextUnit.Unspecified) {
    LazyRow {
        val author =
            if (entry.author.isNullOrBlank()) null else entry.author!!
        author?.let {
            item {
                AssistChip(
                    onClick = { /*TODO*/ },
                    label = { Text(text = author, fontSize = fontSize) },
                    colors = AssistChipDefaults.elevatedAssistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        labelColor = MaterialTheme.colorScheme.onPrimary,
                        leadingIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.padding(end = 3.dp)
                )
            }
        }
        val zonedDateTime = ZonedDateTime.parse(entry.createdAt ?: "")
        val now = ZonedDateTime.now()
        val period = Period.between(zonedDateTime.toLocalDate(), now.toLocalDate())
        val duration = Duration.between(zonedDateTime, now)
        val displayDate = when {
            period.years > 0 -> "${period.years} years ago"
            period.months > 0 -> "${period.months} months ago"
            period.days > 3 -> "${period.days} days ago"
            period.days > 0 -> "${period.days} days ago"
            duration.toHours() > 0 -> "${duration.toHours()} hours ago"
            duration.toMinutes() > 0 -> "${duration.toMinutes()} min ago"
            else -> "Just now"
        }

        item {
            AssistChip(
                onClick = { /*TODO*/ },
                label = { Text(text = displayDate, fontSize = fontSize) },
                colors = AssistChipDefaults.elevatedAssistChipColors(
                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    labelColor = MaterialTheme.colorScheme.onTertiary
                ),
                modifier = Modifier.padding(end = 3.dp)
            )
        }

        item {
            AssistChip(
                onClick = { /*TODO*/ },
                label = { Text(text = entry.feed?.title?.take(10) ?: "", fontSize = fontSize) },
                colors = AssistChipDefaults.elevatedAssistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    labelColor = MaterialTheme.colorScheme.onSecondary,
                    leadingIconContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

@Composable
fun EntriesScreen(entryViewModel: EntryViewModel, navController: NavHostController) {
    if (entryViewModel.state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
    entryViewModel.state.error?.let { error ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = error, color = Color.Red)
                Button(onClick = { /* start view model loading again */ }) {
                    Text(text = "Try again")
                }
            }
        }
    }
    entryViewModel.state.entriesDto?.let { entries ->
        val entriesList = entries.entries
        Entries(entriesList, navController)
    }
}

@Composable
fun Entries(entries: ArrayList<EntryDto>, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        LazyColumn(content = {
            items(entries) { entryDto ->
                Box(
                    modifier = Modifier.clickable {
                        navController.navigate("${ScreenItem.Entries.route}/${entryDto.id}")
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clip(RoundedCornerShape(size = 6.dp))
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = entryDto.title ?: "Unknown title",
                            fontWeight = FontWeight.Bold
                        )
                        ChipsForEntry(entry = entryDto, TextUnit(3f, TextUnitType.Em))
//                        Text(text = entryDto.content ?: "")
                    }

                }
            }
        })
    }
}

@Composable
fun FeedsScreen(
    feedViewModel: FeedViewModel
) {
    if (feedViewModel.state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
    feedViewModel.state.error?.let { error ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = error, color = Color.Red)
                Button(onClick = { /* start view model loading again */ }) {
                    Text(text = "Try again")
                }
            }
        }
    }
    feedViewModel.state.feedDtos?.let {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            LazyColumn(content = {
                items(items = feedViewModel.state.feedDtos ?: arrayOf()) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clip(RoundedCornerShape(size = 6.dp))
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 5.dp)
                        )
                        Column {
                            Text(
                                text = it.title ?: "Unknown feed title",
                                fontWeight = FontWeight.Bold
                            )
                            val link = it.feedUrl ?: "Unknown link"
                            var displayedLink: String = link
                            displayedLink =
                                if (URLUtil.isValidUrl(displayedLink)) displayedLink.getDomainName() else displayedLink
                            Text(
                                text = "Source: $displayedLink",
                                fontSize = TextUnit(3f, TextUnitType.Em)
                            )
                            val displayedCategory = it.category?.title ?: ""
                            Text(
                                text = "Category: $displayedCategory",
                                fontSize = TextUnit(3f, TextUnitType.Em)
                            )
                        }
                    }
                }
            })
        }
    }
}

private fun String.getDomainName(): String {
    val uri = URI(this)
    val domain = uri.host
    return if (domain.startsWith("www")) domain.slice(4..domain.length - 1) else domain
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserButton(state: CurrentUserState) {
    var expanded by remember { mutableStateOf(false) }

    state.userDto?.let { data ->
        var offset = Offset.Zero
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .padding(0.dp)
                .size(130.dp)
                .pointerInteropFilter { offset = Offset(it.x, it.y); false },
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 3.dp)
                )
                var upperBound = 6
                upperBound = min(upperBound, data.username.length - 1)
                val slicedUsername = data.username.slice(0..upperBound)
                val displayedUsename =
                    if (slicedUsername != data.username) (slicedUsername + "..") else data.username
                Text(text = displayedUsename, color = Color.White)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(offset.x.dp, offset.y.dp)
        ) {
            DropdownMenuItem(
                onClick = { /* Handle click */ },
                text = {
                    Text(text = "Text here is")
                }
            )
        }

    }
}

@Preview(showBackground = true, device = "id:pixel_5", showSystemUi = true)
@Composable
fun GreetingPreview() {
    val stateHappyPath = CurrentUserState(
        userDto = UserDto(
            id = 1,
            username = "archie",
            isAdmin = true,
            "", "", "", "",
            "", "", "", "",
            1, false, false, false,
            "", "", "", 1,
            1, "", ""
        ),
        false, null
    )
    val stateInProgress = CurrentUserState(userDto = null, isLoading = true, error = null)
    val stateBadPath = CurrentUserState(userDto = null, isLoading = false, error = "ERROOORR")
    val state = stateHappyPath
    Hseandroidlab3Theme {
    }
}