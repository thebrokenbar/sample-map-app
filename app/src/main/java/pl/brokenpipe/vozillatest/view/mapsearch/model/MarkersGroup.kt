package pl.brokenpipe.vozillatest.view.mapsearch.model

import pl.brokenpipe.vozillatest.view.mapsearch.model.MapColor

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class MarkersGroup(
        val id: String,
        val clusterColor: MapColor,
        val options: String? = null
)