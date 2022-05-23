package ducletran.tech.imutransformer.ui.datacollection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import ducletran.tech.imutransformer.ui.theme.OrangeLight
import ducletran.tech.imutransformer.ui.viewmodel.ExperimentListViewModel
import ducletran.tech.imutransformer.R
import ducletran.tech.imutransformer.ui.navigation.IMUScreen

data class Experiment(
    val name: String,
    val highlighted: Boolean,
    val id: Long
)

@Composable
fun ExperimentListScreenWithNav(navController: NavController) {
    val experimentListViewModel: ExperimentListViewModel = viewModel()
    val experiments = experimentListViewModel.experimentsState.collectAsState().value
        .map {
            Experiment(
                highlighted = true,
                name = it.description,
                id = it.id
            )
        }
    ExperimentListScreen(
        modifier = Modifier.fillMaxSize(),
        experiments = experiments,
        onExperimentClick = {
            navController.navigate(IMUScreen.RunExperiment.formatRouteWithId(it.id))
        },
        onCustomExperienceClick = {
            navController.navigate(IMUScreen.CustomExperimentSetup.route)
        },
        onStepTrackingClick = {
            navController.navigate(IMUScreen.StepTracking.route)
        }
    )
}


@Composable
private fun ExperimentListScreen(
    modifier: Modifier = Modifier,
    experiments: List<Experiment>,
    onExperimentClick: (Experiment) -> Unit,
    onCustomExperienceClick: () -> Unit,
    onStepTrackingClick: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        item("step_tracking") {
            ExperimentCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                experiment = Experiment(
                    name = stringResource(id = R.string.step_tracking),
                    highlighted = true,
                    id = -1L
                ),
                onClick = onStepTrackingClick
            )
        }
        item("custom_experience") {
            ExperimentCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                experiment = Experiment(
                    name = stringResource(id = R.string.custom_experiment),
                    highlighted = true,
                    id = -1L
                ),
                onClick = onCustomExperienceClick
            )
        }
        item("top_spacer") {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.select_experiment_description),
                fontWeight = FontWeight.Bold
            )
        }
        val (highlightedExperiments, normalExperiments) = experiments.partition { it.highlighted }

        items(highlightedExperiments, key = { it.name }) { exp ->
            ExperimentCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                experiment = exp,
                onClick = { onExperimentClick(exp) }
            )
        }

        items(normalExperiments, key = { it.name }) { exp ->
            ExperimentCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                experiment = exp,
                onClick = { onExperimentClick(exp) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExperimentCard(
    modifier: Modifier = Modifier,
    experiment: Experiment,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            if (experiment.highlighted) {
                Icon(
                    Icons.Rounded.Star,
                    null,
                    tint = OrangeLight
                )
                Spacer(Modifier.width(16.dp))
            }

            Text(text = experiment.name.capitalize(Locale.current))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewExperimentList() {
    IMUTransformerTheme {
        ExperimentListScreen(
            experiments = listOf(
                Experiment("Walking Test", true, 1L),
                Experiment("All kind of stuff with phone location", false, 2L),
                Experiment("Standing Test", true, 3L),
                Experiment("Running Test", false, 4L),
                Experiment("Teaching Test", false, 5L)
            ),
            onExperimentClick = { },
            onCustomExperienceClick = { },
            onStepTrackingClick = { }
        )
    }
}