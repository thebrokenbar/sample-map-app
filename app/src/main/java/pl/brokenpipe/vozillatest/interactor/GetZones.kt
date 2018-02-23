package pl.brokenpipe.vozillatest.interactor

import pl.brokenpipe.vozillatest.interactor.model.ZoneModel

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
class GetZones(zoneCache: Map<String, ZoneModel>) : GetMapObjectsFromCache<ZoneModel>(zoneCache)