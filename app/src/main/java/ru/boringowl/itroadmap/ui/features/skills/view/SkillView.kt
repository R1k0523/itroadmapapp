package ru.boringowl.itroadmap.ui.features.skills.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.competence.Skill

@Composable
fun SkillView(s: Skill) = Card(
    Modifier
        .fillMaxWidth()
        .padding(4.dp),
    colors = CardDefaults.cardColors(containerColor = colorScheme.primaryContainer)
){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = s.skillName,
            style = MaterialTheme.typography.titleLarge.copy(color = colorScheme.onSurface),
            fontWeight = FontWeight.Normal,
        )
        Text(
            text = "${stringResource(R.string.popularity)}: ${s.necessity}",
            style = MaterialTheme.typography.labelLarge.copy(color = colorScheme.onSurface),
            fontWeight = FontWeight.Light
        )
    }
}
