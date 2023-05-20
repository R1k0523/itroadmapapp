package ru.boringowl.itroadmap.ui.features.routes.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.ui.base.lists.rememberForeverLazyListState
import ru.boringowl.itroadmap.ui.components.bar.BaseSearchTopBar
import ru.boringowl.itroadmap.ui.features.routes.state.RoutesEvent
import ru.boringowl.itroadmap.ui.features.routes.viewmodel.RoutesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun RoutesScreen(
    viewModel: RoutesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) = Column(
    modifier = Modifier
        .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
) {

    val state by viewModel.uiState.collectAsState()
    if (state.isDialogOpened)
        CreateDialog(viewModel)
    Scaffold(
        topBar = { RoutesTopBar(viewModel) },
        floatingActionButtonPosition = FabPosition.End,
    ) { p ->
        Column(
            modifier = Modifier
                .padding(p)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (state.routes.isEmpty() && !state.loading) {
                Text(
                    text = stringResource(id = R.string.nothing_found),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = rememberForeverLazyListState(stringResource(R.string.nav_routes)),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.routes.sortedBy { it.index() }) { r ->
                    AnimatedVisibility(visible = r.routeName.contains(state.searchText, ignoreCase = true)) {
                        RouteComponent(navigator, viewModel, r)
                    }
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.setEvent(RoutesEvent.Fetch)
    }
}

@Composable
fun RoutesTopBar(viewModel: RoutesViewModel) {
    val state by viewModel.uiState.collectAsState()
    BaseSearchTopBar(
        isSearchOpened = state.isSearchOpened,
        searchText = state.searchText,
        onSearchOpenedEdit = {
            viewModel.setEvent(RoutesEvent.SetSearchOpened(it))
        },
        onSearchTextEdit = {
            viewModel.setEvent(RoutesEvent.SetSearchText(it))
        },
        titleRes = R.string.nav_routes
    )
}