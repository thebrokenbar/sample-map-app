package pl.brokenpipe.vozillatest.interactor

import pl.brokenpipe.vozillatest.interactor.model.PoiModel

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
class GetPois(poiCache: Map<String, PoiModel>) : GetMapObjectsFromCache<PoiModel>(poiCache)