package ru.boringowl.itroadmap.ui.features.todos.list.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.competence.Todo
import ru.boringowl.itroadmap.ui.features.destinations.TodoScreenDestination
import ru.boringowl.itroadmap.ui.features.todos.list.state.TodosEvent
import ru.boringowl.itroadmap.ui.features.todos.list.viewmodel.TodosViewModel

@Composable
fun TodoView(
    navigator: DestinationsNavigator,
    viewModel: TodosViewModel,
    t: Todo,
) {
    var isMenuOpened by remember { mutableStateOf(false) }
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    navigator.navigate(TodoScreenDestination(t.todoId))
                }
                .padding(16.dp),
        ) {
            var progress = t.skills.sumOf { it.progress }
            var all = t.skills.size.times(5)
            if (t.skills.isEmpty()) {
                progress = t.ready
                all = t.full
            }
            Column(Modifier.weight(1f)) {
                Text(
                    t.header,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.CheckBox,
                        "Отмечено",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "$progress/$all",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            Box {
                Icon(
                    Icons.Rounded.MoreVert,
                    stringResource(R.string.more),
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable { isMenuOpened = true }
                )
                DropdownMenu(
                    expanded = isMenuOpened,
                    onDismissRequest = { isMenuOpened = false },
                    modifier = Modifier.height(40.dp)
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.height(40.dp),
                        onClick = {
                            viewModel.setEvent(TodosEvent.DeleteTodo(t.todoId))
                            isMenuOpened = false
                        },
                        text = { Text(stringResource(R.string.delete), Modifier.height(40.dp)) }
                    )
                }
            }
        }
    }
}
