package ru.boringowl.itroadmap.ui.components.bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.spec.Direction
import ru.boringowl.itroadmap.ui.features.destinations.Destination
import ru.boringowl.itroadmap.ui.navigation.NavigationInfo


@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun BottomBar(
    currentDestination: Destination,
    onBottomBarItemClick: (Direction) -> Unit
) {
    val bottomBarState = rememberSaveable { mutableStateOf(false) }

    bottomBarState.value = isMainScreen(destination = currentDestination)

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar {
                NavigationInfo.bottomBarItems.forEach { destination ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = stringResource(id = destination.title)
                            )
                        },
                        label = {
                            Text(stringResource(id = destination.title), style = MaterialTheme.typography.labelSmall)
                        },
                        selected = destination.route.route.startsWith(currentDestination.baseRoute),
                        onClick = { onBottomBarItemClick(destination.route) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                            selectedIconColor = MaterialTheme.colorScheme.onSurface,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                            unselectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }
        }
    )
}


@ExperimentalMaterial3Api
fun isMainScreen(destination: Destination?): Boolean {
    return NavigationInfo.bottomBarItems.map { it.route.route }.contains(destination?.route)
}