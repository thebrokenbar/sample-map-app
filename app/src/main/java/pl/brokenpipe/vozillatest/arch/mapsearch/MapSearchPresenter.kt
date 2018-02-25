package pl.brokenpipe.vozillatest.arch.mapsearch

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import pl.brokenpipe.vozillatest.arch.Presenter
import pl.brokenpipe.vozillatest.view.filters.model.FilterItem
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkersGroup
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
import pl.brokenpipe.vozillatest.view.mapsearch.model.Zone


/**
 * Created by gwierzchanowski on 20.02.2018.
 */
interface MapSearchPresenter : Presenter {
    fun manualRefresh(): Completable
    fun setSearchFilter(searchFilter: SearchFilter): Single<SearchFilter>
    fun getMarkersGroup(): Single<List<MarkersGroup>>
    fun fetchMarkers(): Single<Map<MarkersGroup, List<Marker>>>
    fun getSearchFilterImmediately(): Single<SearchFilter>
    fun observeSearchFilterChanges(): Observable<SearchFilter>
    fun fetchVehicleModelsToFilter(): Single<List<FilterItem>>
    fun fetchVehicleStatusesToFilter(): Single<List<FilterItem>>
    fun getZones(): Single<List<Zone>>
}