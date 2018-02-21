package pl.brokenpipe.vozillatest.mapsearch.cluster

import pl.brokenpipe.vozillatest.mapsearch.model.MapColor

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class Cluster(
        val id: String,
        val clusterColor: MapColor,
        val options: String? = null
)