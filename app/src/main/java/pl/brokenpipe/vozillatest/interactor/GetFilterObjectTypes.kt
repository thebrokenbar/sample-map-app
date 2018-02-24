package pl.brokenpipe.vozillatest.interactor

import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.constant.FilterTypes
import pl.brokenpipe.vozillatest.repository.mapfilter.specification.MapFiltersSpecification

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
class GetFilterObjectTypes(
        mapFiltersRepository: Repository<Map<String, Map<String, String>>, MapFiltersSpecification>
) : GetFilter(mapFiltersRepository) {
    override val filterType = FilterTypes.FILTER_OBJECT_TYPE
}