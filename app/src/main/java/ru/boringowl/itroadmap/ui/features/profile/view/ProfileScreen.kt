package ru.boringowl.itroadmap.ui.features.profile.view

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.data.persistence.file.FileUtils
import ru.boringowl.itroadmap.domain.model.user.User
import ru.boringowl.itroadmap.ui.MainEvent
import ru.boringowl.itroadmap.ui.MainStateViewModel
import ru.boringowl.itroadmap.ui.components.button.AppButton
import ru.boringowl.itroadmap.ui.extensions.AsyncImageById
import ru.boringowl.itroadmap.ui.features.destinations.ProfileEditScreenDestination
import ru.boringowl.itroadmap.ui.features.profile.state.ProfileEvent
import ru.boringowl.itroadmap.ui.features.profile.viewmodel.ProfileViewModel
import ru.boringowl.itroadmap.ui.features.profileedit.state.toInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    mainViewModel: MainStateViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    BackHandler(true) {}
    val state by viewModel.uiState.collectAsState()
    val themeState by mainViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.nav_profile)) },
                actions = {
                    IconButton(onClick = { mainViewModel.setEvent(MainEvent.SetTheme) }) {
                        Icon(
                            imageVector = if (themeState.isDarkTheme()) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { viewModel.setEvent(ProfileEvent.Logout) }) {
                        Icon(
                            imageVector = Icons.Rounded.Logout,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.user?.let { user ->
                NameAndImage(user, viewModel)
                Description(user.description)
                AppButton(
                    onClick = { navigator.navigate(ProfileEditScreenDestination(user.toInfo())) },
                    stringRes = R.string.change_profile,
                    loading = state.loading,
                    modifier = Modifier.fillMaxWidth()
                )
                TextCard(R.string.friends) {  }
                TextCard(R.string.followers) { }
                TextCard(R.string.follows) { }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.setEvent(ProfileEvent.FetchMe)
    }
}


@Composable
fun NameAndImage(user: User, viewModel: ProfileViewModel) {
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                val file = FileUtils.getFileFromUri(context, uri)
                viewModel.setEvent(ProfileEvent.UploadProfileImage(file))
            }
        }
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImageById(
            user.imageId,
            Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
        )
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = user.fullName,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Description(description: String) {
    Text(
        text = stringResource(R.string.description),
        style = MaterialTheme.typography.titleMedium,
    )
    Text(
        text = description.ifEmpty { stringResource(R.string.no_description) },
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun TextCard(
    @StringRes stringId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = ElevatedCard(
    modifier = modifier,
    shape = MaterialTheme.shapes.small
) {
    Column(
        modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 72.dp)
            .clickable { onClick() }
            .padding(16.dp, 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(stringId),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
        )
    }
}