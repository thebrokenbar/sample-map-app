package pl.brokenpipe.vozillatest.interactor

import pl.brokenpipe.vozillatest.interactor.model.ChargerModel


/**
 * Created by gwierzchanowski on 22.02.2018.
 */
class GetChargers(chargerCache: Map<String, ChargerModel>) : GetMapObjectsFromCache<ChargerModel>(chargerCache)