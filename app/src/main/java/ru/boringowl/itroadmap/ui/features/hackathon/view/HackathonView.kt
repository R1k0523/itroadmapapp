package ru.boringowl.itroadmap.ui.features.hackathon.view
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import ru.boringowl.itroadmap.ui.components.button.AppButton
import ru.boringowl.itroadmap.ui.components.button.AppOutlinedButton
import ru.boringowl.itroadmap.ui.components.text.TextWithHeader
import ru.boringowl.itroadmap.ui.extensions.openLink

@Composable
fun HackathonView(h: Hackathon) {
    val opened = remember { mutableStateOf(false) }
    val context = LocalContext.current
    ElevatedCard(
        Modifier.fillMaxWidth().padding(bottom = 8.dp)
    ) {
        Column {
            h.imageUrl?.let {
                Box(
                    modifier = Modifier.background(Color.DarkGray).fillMaxSize()
                ) {
                    var placeholder by remember { mutableStateOf(true)}
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it)
                            .crossfade(true)
                            .build(),
                        onSuccess = { placeholder = false},
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(200.dp).placeholder(visible = placeholder,
                            highlight = PlaceholderHighlight.fade(), color = MaterialTheme.colorScheme.secondaryContainer)
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    h.hackTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    h.hackDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light
                )
                AnimatedVisibility(visible = opened.value) {
                    Column(Modifier.fillMaxWidth()) {
                        TextWithHeader(stringResource(R.string.hack_date), h.date)
                        TextWithHeader(stringResource(R.string.hack_focus), h.focus)
                        TextWithHeader(stringResource(R.string.hack_money), h.prize)
                        TextWithHeader(stringResource(R.string.hack_org), h.organization)
                        TextWithHeader(stringResource(R.string.hack_aud), h.routes)
                    }
                }
                Row {
                    AppButton(
                        onClick = { openLink(h.source, context) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 8.dp, end = 4.dp),
                        stringRes = R.string.go_to
                    )
                    AppOutlinedButton(
                        onClick = { opened.value = !opened.value },
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 8.dp, start = 4.dp),
                        stringRes = if (opened.value) R.string.collapse else R.string.more
                    )
                }
            }
        }
    }
}