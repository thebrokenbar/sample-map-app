package pl.brokenpipe.vozillatest.interactor

import pl.brokenpipe.vozillatest.interactor.model.ParkingModel

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
class GetParkings(parkingCache: Map<String, ParkingModel>): GetMapObjectsFromCache<ParkingModel>(parkingCache)