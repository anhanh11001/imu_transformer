package ducletran.tech.imutransformer.ui.navigation

sealed class IMUScreen(val route: String) {

    object CreateLabel : IMUScreen("create_label")
    object CreateExperiment : IMUScreen("create_experiment")
}