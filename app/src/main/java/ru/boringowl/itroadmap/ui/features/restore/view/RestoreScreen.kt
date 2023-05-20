package ru.boringowl.itroadmap.ui.features.restore.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.ui.components.button.AppButton
import ru.boringowl.itroadmap.ui.components.textfield.PasswordTextField
import ru.boringowl.itroadmap.ui.features.restore.state.RestoreEvent
import ru.boringowl.itroadmap.ui.features.restore.state.RestoreStep
import ru.boringowl.itroadmap.ui.features.restore.viewmodel.RestoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun RestoreScreen(
    viewModel: RestoreViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) = Column(
    modifier = Modifier
        .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val state by viewModel.uiState.collectAsState()
    Icon(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier
            .padding(32.dp)
            .size(72.dp),
        tint = MaterialTheme.colorScheme.onSurface
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.restore_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            when (state.restoreStep) {
                RestoreStep.Email -> EmailForm(viewModel)
                RestoreStep.Code -> CodeForm(viewModel)
                RestoreStep.Password -> PasswordForm(viewModel)
                RestoreStep.Finish -> {}
            }
            Text(
                text = state.error?.text() ?: "",
                style = MaterialTheme.typography.labelMedium.copy(
                    if (state.error != null)
                        MaterialTheme.colorScheme.error
                    else Color.Transparent
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(4.dp)
            )
        }
        Text(
            text = stringResource(R.string.remember_password),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        AppButton(
            onClick = {
                navigator.navigateUp()
            },
            stringRes = R.string.login,
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailForm(viewModel: RestoreViewModel) {
    val state by viewModel.uiState.collectAsState()
    OutlinedTextField(
        value = state.form.email,
        onValueChange = { viewModel.setEvent(RestoreEvent.SetForm(state.form.copy(email = it))) },
        label = { Text(stringResource(R.string.email)) },
        modifier = Modifier.fillMaxWidth(),
    )
    AppButton(
        onClick = { viewModel.setEvent(RestoreEvent.GetCode) },
        stringRes = R.string.restore,
        loading = state.loading,
        modifier = Modifier.fillMaxWidth()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeForm(viewModel: RestoreViewModel) {
    val state by viewModel.uiState.collectAsState()
    OutlinedTextField(
        value = state.form.code,
        onValueChange = { viewModel.setEvent(RestoreEvent.SetForm(state.form.copy(code = it))) },
        label = { Text(stringResource(R.string.code)) },
        modifier = Modifier.fillMaxWidth(),
    )
    AppButton(
        onClick = { viewModel.setEvent(RestoreEvent.GetToken) },
        stringRes = R.string.confirm    ,
        loading = state.loading,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordForm(viewModel: RestoreViewModel) {
    val state by viewModel.uiState.collectAsState()
    PasswordTextField(
        value = state.form.password,
        onValueChange = { viewModel.setEvent(RestoreEvent.SetForm(state.form.copy(password = it))) },
        passwordVisible = state.passwordVisible,
        onPasswordVisibleChange = {
            viewModel.setEvent(
                RestoreEvent.SetPasswordVisibility(it)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
    PasswordTextField(
        value = state.form.passwordRepeat,
        onValueChange = { viewModel.setEvent(RestoreEvent.SetForm(state.form.copy(passwordRepeat = it))) },
        passwordVisible = state.passwordRepeatVisible,
        onPasswordVisibleChange = {
            viewModel.setEvent(
                RestoreEvent.SetPasswordRepeatVisibility(it)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
    AppButton(
        onClick = { viewModel.setEvent(RestoreEvent.SetPassword) },
        stringRes = R.string.change_password,
        loading = state.loading,
        modifier = Modifier.fillMaxWidth()
    )
}