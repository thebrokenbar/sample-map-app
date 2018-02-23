package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
data class ChargerModel(
        override val id: String,
        val address: String,
        val parkingId: String?,
        val type: String,
        val location: Point,
        val name: String
) : Model()