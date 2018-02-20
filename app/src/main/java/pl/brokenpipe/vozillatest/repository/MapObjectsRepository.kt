package pl.brokenpipe.vozillatest.repository

import io.reactivex.Maybe
import io.swagger.client.api.MapApi
import io.swagger.client.model.MapSearchResponse
import pl.brokenpipe.vozillatest.arch.Arch


/**
 * Created by gwierzchanowski on 19.02.2018.
 */

class MapObjectsRepository(private val mapApi: MapApi)
    : Arch.Repository<MapSearchResponse, MapSpecification> {

    override fun get(spec: MapSpecification): Maybe<MapSearchResponse> {
        return mapApi.findMapObjects(spec.objectType, spec.vehicleType, spec.vehicleModel,
                spec.vehicleStatus, spec.poiCategory, spec.location)
    }
}