package pl.brokenpipe.vozillatest.repository.map

import io.reactivex.Maybe
import io.reactivex.Observable
import io.swagger.client.api.MapApi
import io.swagger.client.model.MapSearchResponse
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.repository.map.specification.MapSpecification


/**
 * Created by gwierzchanowski on 19.02.2018.
 */

class MapObjectsRepository(private val mapApi: MapApi)
    : Repository<MapSearchResponse, MapSpecification> {

    override fun get(spec: MapSpecification): Observable<MapSearchResponse> {
        return mapApi.findMapObjects(spec.objectType, spec.vehicleType, spec.vehicleModel,
                spec.vehicleStatus, spec.poiCategory, spec.location).toObservable()
    }
}