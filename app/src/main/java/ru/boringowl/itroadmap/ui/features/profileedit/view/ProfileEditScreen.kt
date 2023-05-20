package ru.boringowl.itroadmap.ui.features.profileedit.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.ui.components.button.AppButton
import ru.boringowl.itroadmap.ui.components.textfield.PasswordTextField
import ru.boringowl.itroadmap.ui.features.profileedit.state.PasswordForm
import ru.boringowl.itroadmap.ui.features.profileedit.state.ProfileEditEvent
import ru.boringowl.itroadmap.ui.features.profileedit.state.UserInfo
import ru.boringowl.itroadmap.ui.features.profileedit.viewmodel.ProfileEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination()
fun ProfileEditScreen(
    user: UserInfo,
    viewModel: ProfileEditViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,
    scrollState: ScrollState = rememberScrollState(),
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(colorScheme.primary),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val state by viewModel.uiState.collectAsState()
    if (state.success)
        navigator?.navigateUp()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.nav_profileedit)) },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(scrollState, true),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.user?.let { user ->
                UserView(viewModel, user)
                PasswordView(viewModel, state.passwordForm)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.setEvent(ProfileEditEvent.SetUser(user))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserView(viewModel: ProfileEditViewModel, user: UserInfo) = Column(
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
) {
    OutlinedTextField(
        value = user.username,
        onValueChange = { viewModel.setEvent(ProfileEditEvent.SetUser(user.copy(username = it))) },
        label = { Text(stringResource(R.string.username)) },
        modifier = Modifier.fillMaxWidth(),
    )
    OutlinedTextField(
        value = user.fullName,
        onValueChange = { viewModel.setEvent(ProfileEditEvent.SetUser(user.copy(fullName = it))) },
        label = { Text(stringResource(R.string.full_name)) },
        modifier = Modifier.fillMaxWidth(),
    )
    OutlinedTextField(
        value = user.description,
        onValueChange = { viewModel.setEvent(ProfileEditEvent.SetUser(user.copy(description = it))) },
        label = { Text(stringResource(R.string.description)) },
        modifier = Modifier.fillMaxWidth(),
    )
    OutlinedTextField(
        value = user.email,
        onValueChange = { viewModel.setEvent(ProfileEditEvent.SetUser(user.copy(email = it))) },
        label = { Text(stringResource(R.string.email)) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (user.enabled) Icon(Icons.Rounded.Done, null, tint = colorScheme.primary)
            else Icon(Icons.Rounded.Cancel, null, tint = colorScheme.error)
        },
    )
    if (!user.enabled) EmailForm(viewModel)
    AppButton(
        onClick = { viewModel.setEvent(ProfileEditEvent.UpdateUser) },
        stringRes = R.string.user_update
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailForm(viewModel: ProfileEditViewModel) = Column(
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
) {
    val state by viewModel.uiState.collectAsState()
    Text(text = stringResource(R.string.profile_not_activated))
    if (state.verificationForm.canBeRequested())
        AppButton(
            onClick = { viewModel.setEvent(ProfileEditEvent.GetCode) },
            stringRes = R.string.get_code,
        )
    if (state.verificationForm.codeRequested != 0L)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.height(72.dp)
        ) {
            OutlinedTextField(
                value = state.verificationForm.code,
                onValueChange = { viewModel.setEvent(ProfileEditEvent.SetCode(it)) },
                label = { Text(stringResource(R.string.code)) },
                modifier = Modifier.weight(1f),
            )
            IconButton(
                onClick = { viewModel.setEvent(ProfileEditEvent.SendCode) },
                modifier = Modifier.height(72.dp)
            ) {
                Icon(Icons.Rounded.Send, null)
            }
        }
}

@Composable
fun PasswordView(viewModel: ProfileEditViewModel, passwordForm: PasswordForm) = Column(
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
) {
    PasswordTextField(
        value = passwordForm.currentPassword,
        onValueChange = {
            viewModel.setEvent(
                ProfileEditEvent.SetPassword(
                    passwordForm.copy(
                        currentPassword = it
                    )
                )
            )
        },
        passwordVisible = passwordForm.currentPasswordVisible,
        onPasswordVisibleChange = {
            viewModel.setEvent(
                ProfileEditEvent.SetPassword(
                    passwordForm.copy(
                        currentPasswordVisible = it
                    )
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.password)) },
    )
    PasswordTextField(
        value = passwordForm.newPassword,
        onValueChange = {
            viewModel.setEvent(
                ProfileEditEvent.SetPassword(
                    passwordForm.copy(
                        newPassword = it
                    )
                )
            )
        },
        passwordVisible = passwordForm.newPasswordVisible,
        onPasswordVisibleChange = {
            viewModel.setEvent(
                ProfileEditEvent.SetPassword(
                    passwordForm.copy(
                        newPasswordVisible = it
                    )
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.new_password)) },
    )
    PasswordTextField(
        value = passwordForm.matchingPassword,
        onValueChange = {
            viewModel.setEvent(
                ProfileEditEvent.SetPassword(
                    passwordForm.copy(
                        matchingPassword = it
                    )
                )
            )
        },
        passwordVisible = passwordForm.matchingPasswordVisible,
        onPasswordVisibleChange = {
            viewModel.setEvent(
                ProfileEditEvent.SetPassword(
                    passwordForm.copy(
                        matchingPasswordVisible = it
                    )
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.passwordAgain)) },
    )
    AppButton(
        onClick = { viewModel.setEvent(ProfileEditEvent.UpdatePassword) },
        stringRes = R.string.update_password,
    )
}