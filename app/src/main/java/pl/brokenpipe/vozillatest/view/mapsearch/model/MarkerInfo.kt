package pl.brokenpipe.vozillatest.view.mapsearch.model

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
sealed class MarkerInfo {
    data class VehicleInfo(
            private var rangeKm: String,
            var name: String,
            var platesNumber: String,
            var reservationEnd: String?,
            var batteryLevelPct: Int,
            var picture: String
    ): MarkerInfo()
}