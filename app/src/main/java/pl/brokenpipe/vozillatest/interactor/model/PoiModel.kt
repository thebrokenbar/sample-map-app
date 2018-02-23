package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
data class PoiModel(
        override val id: String,
        val location: Point,
        val name: String
) : Model()