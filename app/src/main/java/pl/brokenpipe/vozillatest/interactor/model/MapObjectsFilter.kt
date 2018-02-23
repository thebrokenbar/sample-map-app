package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
data class MapObjectsFilter(
        val getVehicles: Boolean,
        val getParkings: Boolean,
        val getChargers: Boolean,
        val getPois: Boolean,
        val getZones: Boolean,
        val vehiclesFilter: VehiclesFilter? = null
)