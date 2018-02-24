package pl.brokenpipe.vozillatest.repository.mapfilter

import io.reactivex.Observable
import io.swagger.client.api.MapApi
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.repository.mapfilter.specification.MapFiltersSpecification

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
class MapFiltersRepository(
        mapApi: MapApi
) : Repository<Map<String, Map<String, String>>, MapFiltersSpecification> {

    private val filtersRequestCachedSingle = mapApi.filters().cache()

    override fun get(spec: MapFiltersSpecification): Observable<Map<String, Map<String, String>>> {
        return filtersRequestCachedSingle
                .map { it.filters }
                .toObservable()
    }
}