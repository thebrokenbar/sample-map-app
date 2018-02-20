package pl.brokenpipe.vozillatest.repository

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class MapSpecification(
        val objectType: List<String>? = null,
        val vehicleType: List<String>? = null,
        val vehicleModel: List<String>? = null,
        val vehicleStatus: List<String>? = null,
        val poiCategory: List<String>? = null,
        val location: String? = null
)