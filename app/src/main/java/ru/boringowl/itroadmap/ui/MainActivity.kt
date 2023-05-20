package ru.boringowl.itroadmap.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.boringowl.itroadmap.ui.components.bar.BottomBar
import ru.boringowl.itroadmap.ui.features.NavGraphs
import ru.boringowl.itroadmap.ui.features.destinations.ProfileScreenDestination
import ru.boringowl.itroadmap.ui.features.splash.SplashScreen
import ru.boringowl.itroadmap.ui.navigation.MainScaffold
import ru.boringowl.itroadmap.ui.theme.ITRoadmapAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RoadmapRoot()
        }
    }
}


@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun RoadmapRoot(
    viewModel: MainStateViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    ITRoadmapAppTheme(state.isDarkTheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {

            val engine = rememberAnimatedNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter,
                rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                defaultAnimationsForNestedNavGraph = mapOf(
                    NavGraphs.root to NestedNavGraphDefaultAnimations(
                        enterTransition = { slideInHorizontally() },
                        exitTransition = { slideOutHorizontally() }
                    ),
                ))
            val navController = engine.rememberNavController()
            val startRoute = if (state.signedIn) ProfileScreenDestination else NavGraphs.root.startRoute
            if (state.splashEnabled) {
                SplashScreen()
            } else {
                MainScaffold(
                    navController,
                    {},
                    {
                        BottomBar(currentDestination = it) { direction ->
                            navController.navigate(direction) {
                                launchSingleTop = true
                            }
                        }
                    }
                ) {
                    DestinationsNavHost(
                        engine = engine,
                        navController = navController,
                        navGraph = NavGraphs.root,
                        startRoute = startRoute
                    )
                }
            }
        }
        LaunchedEffect(true) {
            viewModel.setEvent(MainEvent.FetchData)
            delay(3100)
            viewModel.setEvent(MainEvent.DisableSplash)
        }
    }
}