package com.meskitah.itmc.presentation.flicker_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.meskitah.itmc.R
import com.meskitah.itmc.core.util.UiEvent
import com.meskitah.itmc.presentation.components.ImageItem
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickerScreen(
    viewModel: FlickerViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var isRefreshing by remember { mutableStateOf(false) }
    var searchText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    isRefreshing = false
                }

                is UiEvent.ShowSnackbar -> {
                    isRefreshing = false
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

    val collapsed = 22
    val expanded = 28
    val topAppBarTextSize =
        (collapsed + (expanded - collapsed) * (1 - scrollBehavior.state.collapsedFraction)).sp
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LargeTopAppBar(
                title = {
                    if (scrollBehavior.state.collapsedFraction > 0.4f) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.home_screen_title),
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = topAppBarTextSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                viewModel.onEvent(
                                    FlickerEvent.OnLoadImages(
                                        searchText,
                                        context
                                    )
                                )
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            label = {
                                Text(
                                    text = stringResource(R.string.search),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Search, "search icon")
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.type_input),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                },
                navigationIcon = {
                    if (scrollBehavior.state.collapsedFraction > 0.4f)
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "Home Screen Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Home Screen Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Text(
                                text = stringResource(R.string.home_screen_title),
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = topAppBarTextSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.onEvent(FlickerEvent.OnReloadImages(context))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (viewModel.state.isError) {
                //region API Error
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.error_image),
                        contentDescription = "Error Image",
                        modifier = Modifier.size(200.dp)
                    )

                    Text(
                        text = stringResource(R.string.oops),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.error_something_went_wrong),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                //endregion
            } else if (viewModel.state.flickerImage?.items != null) {
                //region Image list
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        viewModel.state.flickerImage!!.items!!,
                        key = { item -> item.media?.m ?: UUID.randomUUID() },
                    ) { item ->
                        ImageItem(item)
                    }
                }
                //endregion
            } else {
                //region Empty List
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.no_image),
                        contentDescription = "No Sports Image",
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = stringResource(R.string.no_images),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.no_images_message),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
                //endregion
            }
        }

        //region Loading state
        if (viewModel.state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(0.4F))
            ) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }
        //endregion
    }
}