package ru.boringowl.itroadmap.ui.features.company.view

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
import ru.boringowl.itroadmap.ui.features.company.state.CompanyEvent
import ru.boringowl.itroadmap.ui.features.company.viewmodel.CompanyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun CompanyScreen(
    navigator: DestinationsNavigator? = null,
    viewModel: CompanyViewModel = hiltViewModel(),
) {
    val listState = rememberForeverLazyListState(stringResource(R.string.nav_company))
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    val items = state.companys.collectAsLazyPagingItems()
    Scaffold(
        topBar = { CompanyTopBar(viewModel)   },
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
                key = { c -> c.uniqueName },
            ) { c -> c?.let { CompanyView(it) } }
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
        viewModel.setEvent(CompanyEvent.Fetch)
    }
}



@Composable
fun CompanyTopBar(viewModel: CompanyViewModel) {
    val state by viewModel.uiState.collectAsState()
    BaseSearchTopBar(
        isSearchOpened = state.isSearchOpened,
        searchText = state.searchText,
        onSearchOpenedEdit = {
            viewModel.setEvent(CompanyEvent.SetSearchOpened(it))
        },
        onSearchTextEdit = {
            viewModel.setEvent(CompanyEvent.UpdateSearch(it))
        },
        titleRes = R.string.nav_company
    )
}