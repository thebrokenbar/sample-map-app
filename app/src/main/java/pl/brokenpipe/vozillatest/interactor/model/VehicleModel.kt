package pl.brokenpipe.vozillatest.interactor.model

import java.util.*

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
data class VehicleModel(
        override val id: String,
        val rangeKm: Long,
        val name: String,
        val platesNumber: String,
        val reservationEnd: Date?,
        val batteryLevelPct: Int,
        val picture: String,
        val color: String,
        val location: Point,
        val status: VehicleStatus,
        val sideNumber: String
) : Model()