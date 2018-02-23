package pl.brokenpipe.vozillatest.interactor.model

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
sealed class VehicleStatus {
    class Available: VehicleStatus()
    class Reserved: VehicleStatus()
    class Rented: VehicleStatus()
    class Returned: VehicleStatus()
    class Unavailable: VehicleStatus()
}