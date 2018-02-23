package pl.brokenpipe.vozillatest.view.mapsearch.model

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
data class SearchFilter(
        val objectTypes: List<String>,
        val vehicleStatus: String? = null,
        val vehicleModel: String? = null
)