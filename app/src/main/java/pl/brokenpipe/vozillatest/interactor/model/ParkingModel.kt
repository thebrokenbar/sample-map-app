package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
data class ParkingModel(
        override val id: String,
        val spacesCount: Int,
        val availableSpacesCount: Int,
        val location: Point,
        val address: String
) : Model()