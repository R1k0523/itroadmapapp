package ru.boringowl.itroadmap.ui.features.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
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
import ru.boringowl.itroadmap.ui.features.auth.state.AuthEvent
import ru.boringowl.itroadmap.ui.features.auth.viewmodel.AuthViewModel
import ru.boringowl.itroadmap.ui.features.destinations.RegisterScreenDestination
import ru.boringowl.itroadmap.ui.features.destinations.RestoreScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primaryContainer),
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
    ElevatedCard(
        shape = MaterialTheme.shapes.large.copy(
            bottomStart = CornerSize(0f),
            bottomEnd = CornerSize(0f)
        ),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.auth_title),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = { viewModel.setEvent(AuthEvent.SetUsername(it) )},
                    label = { Text(stringResource(R.string.username)) },
                    modifier = Modifier.fillMaxWidth(),
                )
                PasswordTextField(
                    value = state.password,
                    onValueChange = { viewModel.setEvent(AuthEvent.SetPassword(it)) },
                    passwordVisible = state.passwordVisible,
                    onPasswordVisibleChange = { viewModel.setEvent(AuthEvent.SetPasswordVisibility(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
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
                AppButton(
                    onClick = { viewModel.setEvent(AuthEvent.Authenticate) },
                    stringRes = R.string.login,
                    loading = state.loading,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.labelLarge.copy(MaterialTheme.colorScheme.onSurface),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { navigator.navigate(RestoreScreenDestination) }
                        .padding(8.dp)
                )
            }
            Text(
                text = stringResource(R.string.no_account),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            AppButton(
                onClick = { navigator.navigate(RegisterScreenDestination) },
                stringRes = R.string.register,
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background,
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppButton(
                    onClick = { },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.github)
                )
                AppButton(
                    onClick = { },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    painter = painterResource(R.drawable.google)
                )
            }
        }
    }
}