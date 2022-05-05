package ducletran.tech.imutransformer.ui.navigation

sealed class IMUScreen(val route: String) {

    object CreateLabel : IMUScreen("create_label")
    object CreateExperiment : IMUScreen("create_experiment")
    object RunExperiment : IMUScreen("experiment/{id}") {
        fun formatRouteWithId(id: Long) = "experiment/$id"
    }
    object CustomExperimentSetup : IMUScreen("custom_experiment")
}