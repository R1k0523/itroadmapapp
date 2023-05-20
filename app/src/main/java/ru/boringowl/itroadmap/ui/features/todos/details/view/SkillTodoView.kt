package ru.boringowl.itroadmap.ui.features.todos.details.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.ui.features.todos.details.state.TodoEvent
import ru.boringowl.itroadmap.ui.features.todos.details.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillTodoView(t: SkillTodo, viewModel: TodoViewModel) {
    var full by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf(t.notes) }
    val focuser = LocalFocusManager.current
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable {
                    full = !full
                    focuser.clearFocus()
                }
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(modifier = Modifier.size(28.dp),
                    onClick = {
                        viewModel.setEvent(TodoEvent.UpdateSkillTodo(t.copy(favorite = !t.favorite)))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = stringResource(R.string.favorite),
                        tint = if (t.favorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                            0.2f
                        )
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        t.manualName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "${stringResource(R.string.neccesity)}: ${t.necessity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = if (full) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = stringResource(R.string.close),
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(30.dp)
                )
            }
            if (full) {
                if (t.manualName.lowercase() != t.skillName.lowercase())
                    Text(
                        text = "${stringResource(R.string.skill)}: ${t.skillName}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                Spacer(Modifier.height(8.dp))
                ProgressDropdownMenu(t.progress) {
                    viewModel.setEvent(TodoEvent.UpdateSkillTodo(t.copy(progress = it)))
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = {
                        notes = it
                        viewModel.setEvent(TodoEvent.UpdateSkillTodo(t.copy(notes = it)))
                    },
                    label = { Text(stringResource(R.string.notes)) },
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                )
            }
        }
    }
}

