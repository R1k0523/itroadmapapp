package ru.boringowl.itroadmap.ui.features.company.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.company.Company
import ru.boringowl.itroadmap.domain.model.company.CompanyRating
import ru.boringowl.itroadmap.ui.components.button.AppOutlinedButton
import ru.boringowl.itroadmap.ui.theme.ITRoadmapAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CompanyView(c: Company) {
    var opened by remember { mutableStateOf(false) }
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(c.name, style = typography.titleLarge, modifier = Modifier.weight(1f))
                        if (!opened && c.ratings.isNotEmpty())
                            Card(colors = CardDefaults.cardColors(containerColor = colorScheme.secondaryContainer)) {
                                Text(c.ratings.last().value.toString(), modifier = Modifier.padding(2.dp))
                            }
                    }
                    Text(c.description, style = typography.bodyMedium)
                }
                var placeholder by remember { mutableStateOf(true) }
                Box(Modifier.clip(RoundedCornerShape(8.dp))) {

                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(c.logo)
                            .crossfade(true)
                            .build(),
                        onSuccess = { placeholder = false },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .placeholder(
                                visible = placeholder,
                                highlight = PlaceholderHighlight.fade(),
                                color = colorScheme.secondaryContainer
                            )

                    )
                }
            }
            AnimatedVisibility (opened) {
                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        stringResource(R.string.employee_rating),
                        style = typography.titleMedium
                    )
                    Card(colors = CardDefaults.cardColors(containerColor = colorScheme.secondaryContainer)) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            if (c.ratings.isEmpty())
                                Text(
                                    text = stringResource(R.string.no_rating),
                                    Modifier.fillMaxWidth(),
                                    style = typography.labelMedium.copy(
                                        textAlign = TextAlign.Center,
                                        color = colorScheme.onSurface
                                    )
                                )
                            c.ratings.subList(maxOf(c.ratings.size - 3, 0), c.ratings.size).forEach {
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        it.value.toString(),
                                        Modifier.fillMaxWidth(),
                                        style = typography.titleMedium.copy(
                                            textAlign = TextAlign.Center,
                                            color = colorScheme.onSurface
                                        )
                                    )
                                    Text(
                                        it.year.toString(),
                                        Modifier.fillMaxWidth(),
                                        style = typography.labelMedium.copy(
                                            textAlign = TextAlign.Center,
                                            color = colorScheme.onSurface
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        stringResource(R.string.tech_stack),
                        style = typography.titleMedium
                    )
                    FlowRow(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        c.skills.forEach {
                            val color = remember { randomColor() }
                            Card(
                                shape = MaterialTheme.shapes.small,
                                colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f)),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    it,
                                    modifier = Modifier.padding(4.dp),
                                    color = color,
                                    style = typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppOutlinedButton(
                    onClick = { opened = !opened },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, start = 4.dp),
                    stringRes = if (opened) R.string.collapse else R.string.more
                )
            }
        }
    }
}


val company = Company(
    uniqueName = "Лаборатория Касперского",
    name = "Лаборатория Касперского",
    description = "Крупнейшая цифровая платформа. Технобренд, объединяющий лучшие мировые практики и самый современный стек",
    skills = listOf(
        "SQL",
        "Java",
        "REST",
        "Git",
        "PostgreSQL",
        "Java Spring Framework",
        "Docker",
        "Linux",
        "SOAP",
        "BPMN",
        "UML",
        "C#",
        "JavaScript",
        "Spring Boot",
        "Python",
        "Kubernetes",
        "Базы данных",
        "Apache Kafka",
        "XML",
        "TypeScript",
        "Hibernate",
        "Системный анализ",
        "C++",
        "React",
        "Тестирование API",
        "Тестирование ПО",
        "Oracle",
        ".NET Core",
        "CI/CD",
        "ООП"
    ),
    ratings = listOf(
        CompanyRating(2018, 3.21),
        CompanyRating(2019, 3.08),
        CompanyRating(2020, 4.54),
        CompanyRating(2021, 4.32),
        CompanyRating(2022, 4.6),
    ),
    logo = "https://habrastorage.org/getpro/moikrug/uploads/company/431/936/411/logo/medium_2dd003e4f8c3fcb5dbc6ceb9876c3c40.png",
)

@Preview
@Composable
fun CompanyView3() {

    ITRoadmapAppTheme(false) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            color = colorScheme.background
        ) {
            CompanyView(company)
        }
    }
}

@Preview
@Composable
fun CompanyView2() {

    ITRoadmapAppTheme(true) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            color = colorScheme.background
        ) {
            CompanyView(company)
        }
    }
}

val colors = listOf(
    Color(0xFF994268),
    Color(0xFF247996),
    Color(0xFF368557),
    Color(0xFFC49B00),
    Color(0xFF7A89C2),
    Color(0xFF9C9C06),
    Color(0xFF098F84),
)

fun randomColor() = colors.random()