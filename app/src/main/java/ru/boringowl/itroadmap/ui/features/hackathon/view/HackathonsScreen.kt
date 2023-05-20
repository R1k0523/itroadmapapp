package ru.boringowl.itroadmap.ui.features.hackathon.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.ui.base.lists.rememberForeverLazyListState
import ru.boringowl.itroadmap.ui.components.bar.BaseSearchTopBar
import ru.boringowl.itroadmap.ui.features.hackathon.state.HackathonsEvent
import ru.boringowl.itroadmap.ui.features.hackathon.viewmodel.HackathonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun HackathonsScreen(
    navigator: DestinationsNavigator? = null,
    viewModel: HackathonViewModel = hiltViewModel(),
) {
    val listState = rememberForeverLazyListState(stringResource(R.string.nav_hackathon))
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    val items = state.hackathons.collectAsLazyPagingItems()
    Scaffold(
        topBar = { HackathonTopBar(viewModel)   },
        floatingActionButton = {
            if (listState.firstVisibleItemIndex != 0) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                        }
                    }) {
                    Icon(
                        Icons.Rounded.ArrowUpward,
                        "Вверх",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
    ) { p ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(p),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = items,
                key = { h -> h.hackId },
            ) { h -> h?.let { HackathonView(it) } }
            if (items.loadState.append == LoadState.Loading || state.loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            item {
                AnimatedVisibility(visible = items.itemCount == 0 && !state.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }

    }
    LaunchedEffect(true) {
        viewModel.setEvent(HackathonsEvent.Fetch)
    }
}



@Composable
fun HackathonTopBar(viewModel: HackathonViewModel) {
    val state by viewModel.uiState.collectAsState()
    BaseSearchTopBar(
        isSearchOpened = state.isSearchOpened,
        searchText = state.searchText,
        onSearchOpenedEdit = {
            viewModel.setEvent(HackathonsEvent.SetSearchOpened(it))
        },
        onSearchTextEdit = {
            viewModel.setEvent(HackathonsEvent.UpdateSearch(it))
        },
        titleRes = R.string.nav_hackathon
    )
}