package pl.brokenpipe.vozillatest.arch.mapsearch

import io.reactivex.Single
import pl.brokenpipe.vozillatest.arch.Presenter
import pl.brokenpipe.vozillatest.view.filters.model.FilterItem
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkersGroup
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter


/**
 * Created by gwierzchanowski on 20.02.2018.
 */
interface MapSearchPresenter : Presenter {
    fun getMarkersGroup(): Single<List<MarkersGroup>>
    fun fetchMapObjects(searchFilter: SearchFilter): Single<Map<MarkersGroup, List<Marker>>>
    fun getSearchFilter(): Single<SearchFilter>
    fun fetchVehicleModelsToFilter(): Single<List<FilterItem>>
    fun fetchVehicleStatusesToFilter(): Single<List<FilterItem>>
}