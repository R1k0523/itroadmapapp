package ru.boringowl.itroadmap.ui.features.routes.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material.icons.rounded.TrendingFlat
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.competence.Route
import ru.boringowl.itroadmap.ui.components.button.AppButton
import ru.boringowl.itroadmap.ui.components.button.AppOutlinedButton
import ru.boringowl.itroadmap.ui.features.destinations.BooksScreenDestination
import ru.boringowl.itroadmap.ui.features.destinations.SkillsScreenDestination
import ru.boringowl.itroadmap.ui.features.routes.state.RoutesEvent
import ru.boringowl.itroadmap.ui.features.routes.viewmodel.RoutesViewModel

@Composable
fun RouteComponent(
    navigator: DestinationsNavigator,
    viewModel: RoutesViewModel,
    r: Route,
) {
    val isTrendingIcon = if (r.index() < 3)
        Icons.Rounded.TrendingUp
    else if (r.index() in 3..6)
        Icons.Rounded.TrendingFlat
    else
        Icons.Rounded.TrendingDown
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { navigator.navigate(SkillsScreenDestination(routeId = r.routeId)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigator.navigate(SkillsScreenDestination(routeId = r.routeId)) }
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    r.routeName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(0.8f)
                )
                Icon(
                    isTrendingIcon,
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                "${stringResource(R.string.vacancies)}: ${r.vacanciesCount}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${stringResource(R.string.resumes)}: ${r.resumesCount}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                r.routeDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
            Row(Modifier.fillMaxWidth()) {
                AppButton(
                    onClick = { viewModel.setEvent(RoutesEvent.SetDialogOpened(true, r.routeId)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, end = 4.dp),
                    stringRes = R.string.create_plan
                )
                AppOutlinedButton(
                    onClick = {
                        navigator.navigate(BooksScreenDestination(routeId = r.routeId))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, start = 4.dp),
                    stringRes = R.string.nav_books
                )
            }
        }
    }
}