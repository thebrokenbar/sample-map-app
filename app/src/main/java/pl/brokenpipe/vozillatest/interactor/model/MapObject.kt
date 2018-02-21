package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
data class MapObject(
        val id: String,
        val position: Pair<Double, Double>,
        val name: String,
        val description: String
)