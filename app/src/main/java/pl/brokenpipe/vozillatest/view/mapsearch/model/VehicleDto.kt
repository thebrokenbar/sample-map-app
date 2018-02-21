package pl.brokenpipe.vozillatest.view.mapsearch.model


data class VehicleDto(
        val name: String,
        val platesNumber: String,
        val sideNumber: String,
        val color: String,
        val picture: Picture,
        val rangeKm: Int,
        val batteryLevelPct: Int,
        val status: String,
        val locationDescription: Any
)

