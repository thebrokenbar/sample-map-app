package pl.brokenpipe.vozillatest.interactor

import io.reactivex.Observable
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.constant.FilterTypes
import pl.brokenpipe.vozillatest.repository.mapfilter.specification.MapFiltersSpecification

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
abstract class GetFilter(
        private val mapFiltersRepository: Repository<Map<String, Map<String, String>>, MapFiltersSpecification>
) : UseCase<Unit, List<Pair<String, String>>> {

    protected abstract val filterType: String

    override fun execute(param: Unit?): Observable<List<Pair<String, String>>> {
        return mapFiltersRepository.get(MapFiltersSpecification())
                .map { mapOfFilters ->
                    val statuses = mapOfFilters[filterType]
                    return@map mutableListOf<Pair<String, String>>().also { list ->
                        statuses?.forEach { (key, value) ->
                            list.add(Pair(key, value))
                        }
                    }
                }
    }
}