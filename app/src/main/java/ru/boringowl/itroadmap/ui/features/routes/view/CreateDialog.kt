package ru.boringowl.itroadmap.ui.features.routes.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.ui.features.routes.state.RoutesEvent
import ru.boringowl.itroadmap.ui.features.routes.viewmodel.RoutesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialog(
    viewModel: RoutesViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    AlertDialog(
        onDismissRequest = { viewModel.setEvent(RoutesEvent.SetDialogOpened(false)) },
        title = {
            Text(
                text = "Создание плана",
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = state.todoName,
                    onValueChange = { viewModel.setEvent(RoutesEvent.SetTodoName(it)) },
                    singleLine = true,
                    label = { Text("Название плана") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Будет создан ваш личный список с навыками, " +
                            "указанными в направлении, где Вы сможете " +
                            "отмечать свой уровень знаний.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            Text(
                stringResource(R.string.save),
                Modifier.clickable { viewModel.setEvent(RoutesEvent.AddTodo) }
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        },
        dismissButton = {
            Text(
                stringResource(R.string.cancel),
                Modifier.clickable { viewModel.setEvent(RoutesEvent.SetDialogDefaults) }
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        },
        shape = MaterialTheme.shapes.small

    )
}