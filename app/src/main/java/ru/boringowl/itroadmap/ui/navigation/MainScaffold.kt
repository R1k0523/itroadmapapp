package ru.boringowl.itroadmap.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.boringowl.itroadmap.ui.features.NavGraphs
import ru.boringowl.itroadmap.ui.features.appDestination
import ru.boringowl.itroadmap.ui.features.destinations.Destination
import ru.boringowl.itroadmap.ui.features.startAppDestination

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScaffold(
    navHostController: NavHostController,
    topBar: @Composable (NavBackStackEntry?) -> Unit,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

    val currentBackStackEntryAsState by navHostController.currentBackStackEntryAsState()
    val destination =
        currentBackStackEntryAsState?.appDestination() ?: NavGraphs.root.startRoute.startAppDestination

    Scaffold(
        topBar = { topBar(currentBackStackEntryAsState) },
        bottomBar = { bottomBar(destination) }
    ) {

        Column(modifier = Modifier.padding(it)) {
            content(it)
        }
    }
}