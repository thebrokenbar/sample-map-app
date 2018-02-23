package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
data class ZoneModel(
        override val id: String,
        val name: String,
        val allowedAreaPolygonPoints: List<List<Point>>,
        val excludedAreaPolygonPoints: List<List<Point>>
) : Model()