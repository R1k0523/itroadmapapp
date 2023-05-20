package ru.boringowl.itroadmap.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CallSplit
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.SafetyDivider
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.vector.ImageVector
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.ui.features.destinations.CompanyScreenDestination
import ru.boringowl.itroadmap.ui.features.destinations.DirectionDestination
import ru.boringowl.itroadmap.ui.features.destinations.HackathonsScreenDestination
import ru.boringowl.itroadmap.ui.features.destinations.ProfileScreenDestination
import ru.boringowl.itroadmap.ui.features.destinations.RoutesScreenDestination
import ru.boringowl.itroadmap.ui.features.destinations.TodosScreenDestination

enum class Routes(
    var route: DirectionDestination,
    @StringRes var title: Int,
    var icon: ImageVector,
) {
    Profile(ProfileScreenDestination, R.string.nav_profile, Icons.Rounded.Person),
    Route(RoutesScreenDestination, R.string.nav_routes, Icons.Rounded.CallSplit),
    Hackathon(HackathonsScreenDestination, R.string.nav_hackathon, Icons.Rounded.SafetyDivider),
    Todo(TodosScreenDestination, R.string.nav_todos, Icons.Rounded.Inventory),
    Company(CompanyScreenDestination, R.string.nav_company, Icons.Rounded.Work),
}

object NavigationInfo {
    val bottomBarItems = arrayListOf(
        Routes.Todo,
        Routes.Route,
        Routes.Company,
        Routes.Hackathon,
        Routes.Profile,
    )
}