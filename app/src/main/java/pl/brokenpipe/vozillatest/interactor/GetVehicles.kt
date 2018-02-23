package pl.brokenpipe.vozillatest.interactor

import pl.brokenpipe.vozillatest.interactor.model.VehicleModel


/**
 * Created by gwierzchanowski on 22.02.2018.
 */
class GetVehicles(vehicleCache: Map<String, VehicleModel>) : GetMapObjectsFromCache<VehicleModel>(vehicleCache)