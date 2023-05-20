package ru.boringowl.itroadmap.ui.features.skills.view

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
import ru.boringowl.itroadmap.ui.features.skills.state.SkillsEvent
import ru.boringowl.itroadmap.ui.features.skills.viewmodel.SkillsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SkillsScreen(
    routeId: Int,
    navigator: DestinationsNavigator? = null,
    viewModel: SkillsViewModel = hiltViewModel(),
) = Column(
    modifier = Modifier
        .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
) {

    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { SkillsTopBar(viewModel) },
        floatingActionButtonPosition = FabPosition.End,
    ) { p ->
        Column(
            modifier = Modifier
                .padding(p)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (state.skills.isEmpty()) {
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
                state = rememberForeverLazyListState(stringResource(R.string.nav_skills)),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.skills.sortedBy { it.skillName }) { s ->

                    AnimatedVisibility(
                        visible = s.skillName.contains(
                            state.searchText,
                            ignoreCase = true
                        )
                    ) {
                        SkillView(s)
                    }
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.setEvent(SkillsEvent.Fetch(routeId))
    }
}

@Composable
fun SkillsTopBar(viewModel: SkillsViewModel) {
    val state by viewModel.uiState.collectAsState()
    BaseSearchTopBar(
        isSearchOpened = state.isSearchOpened,
        searchText = state.searchText,
        onSearchOpenedEdit = {
            viewModel.setEvent(SkillsEvent.SetSearchOpened(it))
        },
        onSearchTextEdit = {
            viewModel.setEvent(SkillsEvent.UpdateSearch(it))
        },
        titleRes = R.string.nav_skills
    )
}