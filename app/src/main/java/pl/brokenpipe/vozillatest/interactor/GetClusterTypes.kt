package pl.brokenpipe.vozillatest.interactor

import io.reactivex.Observable
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.constant.FilterTypes
import pl.brokenpipe.vozillatest.constant.ResourceTypes
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.repository.mapfilter.specification.MapFiltersSpecification
import java.util.logging.Filter

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
class GetClusterTypes(
        private val mapFiltersRepository: Repository<Map<String, Map<String, String>>, MapFiltersSpecification>
) : UseCase<Unit, List<ClusterType>> {

    override fun execute(param: Unit?): Observable<List<ClusterType>> {
        return mapFiltersRepository.get(MapFiltersSpecification())
                .map { mapOfFilters ->
                    val objectTypes = mapOfFilters[FilterTypes.FILTER_OBJECT_TYPE]
                    val vehicleModels = mapOfFilters[FilterTypes.FILTER_VEHICLE_MODEL]

                    return@map mutableListOf<ClusterType>().also { clusterTypes ->
                        parseClusterTypes(objectTypes, clusterTypes, vehicleModels)
                    }
                }
    }

    private fun parseClusterTypes(objectTypes: Map<String, String>?,
                                  clusterTypes: MutableList<ClusterType>,
                                  vehicleModels: Map<String, String>?) {
        objectTypes?.forEach { (key, value) ->
            clusterTypes.add(ClusterType(key, value))
        }

        if (clusterTypes.any { it.id == ResourceTypes.VEHICLE }) {
            vehicleModels?.forEach { (key, value) ->
                clusterTypes.add(ClusterType(ResourceTypes.VEHICLE, value, key))
            }

        }
    }

}