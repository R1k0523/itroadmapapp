package ru.boringowl.itroadmap.ui.features.todos.details.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ru.boringowl.itroadmap.ui.features.todos.details.state.SortTodosBy
import ru.boringowl.itroadmap.ui.features.todos.details.state.TodoEvent
import ru.boringowl.itroadmap.ui.features.todos.details.viewmodel.TodoViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun TodoScreen(
    todoId: UUID,
    navigator: DestinationsNavigator? = null,
    viewModel: TodoViewModel = hiltViewModel(),
) = Column(
    modifier = Modifier
        .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
) {

    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TodosTopBar(viewModel) },
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
                state = rememberForeverLazyListState(stringResource(R.string.nav_todos)),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.skills) { st ->
                    if(state.searchText.length < 3 || st.name().contains(state.searchText, ignoreCase = true)) {
                        SkillTodoView(st, viewModel)
                    }
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.setEvent(TodoEvent.Fetch(todoId))
    }
}

@Composable
fun TodosTopBar(viewModel: TodoViewModel) = Column(horizontalAlignment = Alignment.End) {
    val state by viewModel.uiState.collectAsState()
    var filterOpened by remember { mutableStateOf(false) }
    BaseSearchTopBar(
        isSearchOpened = state.isSearchOpened,
        searchText = state.searchText,
        onSearchOpenedEdit = {
            viewModel.setEvent(TodoEvent.SetSearchOpened(it))
        },
        onSearchTextEdit = {
            viewModel.setEvent(TodoEvent.SetSearchText(it))
        },
        titleRes = R.string.nav_todo,
    )

    Row(
        Modifier
            .padding(8.dp, 4.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { filterOpened = true }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            stringResource(state.sortType.nameRes),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light
        )
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Rounded.Sort,
            contentDescription = stringResource(R.string.filter),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
    }
    Box(contentAlignment = Alignment.BottomEnd) {
        DropdownMenu(
            expanded = filterOpened,
            onDismissRequest = { filterOpened = false },
        ) {
            SortTodosBy.values().forEach { s ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.setEvent(TodoEvent.SetSortBy(s))
                        filterOpened = false
                    },
                    text = { Text(stringResource(s.nameRes)) }
                )
            }
        }
    }
}