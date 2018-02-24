package pl.brokenpipe.vozillatest.presenter.mapsearch

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.interactor.model.*
import pl.brokenpipe.vozillatest.constant.ResourceTypes
import pl.brokenpipe.vozillatest.view.filters.model.FilterItem
import pl.brokenpipe.vozillatest.view.mapsearch.model.*

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class MapSearchViewModel(
        private val getClusterTypes: UseCase<Unit, List<ClusterType>>,
        private val refreshMapObjects: UseCase<MapObjectsFilter, Unit>,
        private val getVehicles: UseCase<String, List<VehicleModel>>,
        private val getParkings: UseCase<String, List<ParkingModel>>,
        private val getChargers: UseCase<String, List<ChargerModel>>,
        private val getPois: UseCase<String, List<PoiModel>>,
        private val getZones: UseCase<String, List<ZoneModel>>,
        private val markerBuilder: MarkerBuilder,
        private val zoneBuilder: ZoneBuilder,
        private val getFilterModels: UseCase<Unit, List<Pair<String, String>>>,
        private val getFilterStatuses: UseCase<Unit, List<Pair<String, String>>>
) : ViewModel(), MapSearchPresenter {

    companion object {
        val CLUSTER_TYPES_COLORS = hashMapOf(
                Pair(ResourceTypes.VEHICLE, MapColor.GreenColor()),
                Pair(ResourceTypes.CHARGER, MapColor.YellowColor()),
                Pair(ResourceTypes.POI, MapColor.OrangeColor()),
                Pair(ResourceTypes.PARKING, MapColor.BlueColor())
        )
        val DEFAULT_CLUSTER_COLOR = MapColor.WhiteColor()
    }

    private lateinit var markersGroupCache: List<MarkersGroup>

    private val defaultSearchFilter = SearchFilter(listOf(ResourceTypes.VEHICLE,
            ResourceTypes.PARKING, ResourceTypes.CHARGER, ResourceTypes.POI, ResourceTypes.ZONE))

    private val searchFilterRelay: PublishSubject<SearchFilter> = PublishSubject.create()
    private var searchFilter: SearchFilter = defaultSearchFilter

    override fun setSearchFilter(searchFilter: SearchFilter): Single<SearchFilter> {
        return Single.fromCallable {
            this.searchFilter = searchFilter
            searchFilterRelay.onNext(searchFilter)
            return@fromCallable searchFilter
        }
    }

    override fun getZones(): Single<List<Zone>> {
        return getZones.execute()
                .flatMap { Observable.fromIterable(it) }
                .map {
                    zoneBuilder.buildZones(it)
                }
                .toList()
                .map { result ->
                    mutableListOf<Zone>().also { zones ->
                        result.forEach {
                            zones.addAll(it)
                        }
                    }
                }
    }

    override fun getMarkersGroup(): Single<List<MarkersGroup>> {
        return getClusterTypes.execute()
                .flatMap { Observable.fromIterable(it) }
                .map { parseClusterTypeToMarkersGroup(it) }
                .toList()
                .doOnSuccess { markersGroupCache = it }
    }

    override fun manualRefresh(): Completable {
        return Completable.fromAction { searchFilterRelay.onNext(searchFilter) }
    }

    override fun getSearchFilter(): Observable<SearchFilter> {
        return searchFilterRelay
    }

    override fun fetchVehicleModelsToFilter(): Single<List<FilterItem>> {
        return getFilterModels.execute()
                .firstOrError()
                .map {
                    it.map { (first, second) ->
                        FilterItem(second, first)
                    }
                }
    }

    override fun fetchVehicleStatusesToFilter(): Single<List<FilterItem>> {
        return getFilterStatuses.execute()
                .firstOrError()
                .map {
                    it.map { (first, second) ->
                        FilterItem(second, first)
                    }
                }
    }

    private fun parseClusterTypeToMarkersGroup(clusterType: ClusterType): MarkersGroup {
        val color = getMapColor(clusterType.id)
        return MarkersGroup(clusterType.id, color, clusterType.options)
    }

    private fun getMapColor(clusterTypeId: String) =
            CLUSTER_TYPES_COLORS.getOrElse(clusterTypeId) { DEFAULT_CLUSTER_COLOR }

    override fun fetchMarkers(): Single<Map<MarkersGroup, List<Marker>>> {
        return Single.defer {
            val mapObjectsFilter = buildMapObjectsFilter(searchFilter)

            val vehicleObservable = if (mapObjectsFilter.getVehicles) {
                prepareVehicleObservable()
            } else {
                Observable.just(hashMapOf())
            }

            val parkingObservable = if (mapObjectsFilter.getParkings) {
                prepareParkingObservable()
            } else {
                Observable.just(hashMapOf())
            }

            val chargerObservable = if (mapObjectsFilter.getChargers) {
                prepareChargerObservable()
            } else {
                Observable.just(hashMapOf())
            }

            val poiObservable = if (mapObjectsFilter.getPois) {
                preparePoiObservable()
            } else {
                Observable.just(hashMapOf())
            }

            return@defer refreshMapObjects.execute(mapObjectsFilter)
                    .firstOrError()
                    .flatMap {
                        concatAllMapObjects(vehicleObservable, parkingObservable,
                                chargerObservable, poiObservable)
                    }
        }
    }

    private fun buildMapObjectsFilter(searchFilter: SearchFilter): MapObjectsFilter {
        return MapObjectsFilter(
                searchFilter.objectTypes.contains(ResourceTypes.VEHICLE),
                searchFilter.objectTypes.contains(ResourceTypes.PARKING),
                searchFilter.objectTypes.contains(ResourceTypes.CHARGER),
                searchFilter.objectTypes.contains(ResourceTypes.POI),
                searchFilter.objectTypes.contains(ResourceTypes.ZONE),
                VehiclesFilter(
                        searchFilter.vehicleStatus,
                        searchFilter.vehicleModel
                )
        )
    }

    private fun concatAllMapObjects(vehicleObservable: Observable<HashMap<MarkersGroup, List<Marker>>>,
                                    parkingObservable: Observable<HashMap<MarkersGroup, List<Marker>>>,
                                    chargerObservable: Observable<HashMap<MarkersGroup, List<Marker>>>,
                                    poiObservable: Observable<HashMap<MarkersGroup, List<Marker>>>
    ): Single<HashMap<MarkersGroup, List<Marker>>> {
        return Observable.concat(vehicleObservable, parkingObservable, chargerObservable, poiObservable)
                .toList()
                .map {
                    return@map hashMapOf<MarkersGroup, List<Marker>>().apply {
                        it.forEach {
                            this.putAll(it)
                        }
                    }
                }
    }

    private fun createMarkersHashMap(resourceType: String, markers: List<Marker>
    ): HashMap<MarkersGroup, List<Marker>> {
        return hashMapOf(Pair(
                markersGroupCache.find { it.id == resourceType }
                        ?: throw IllegalStateException("Could not fin cluster type"),
                markers))
    }

    private fun preparePoiObservable(): Observable<HashMap<MarkersGroup, List<Marker>>> {
        return getPois.execute()
                .map {
                    it.map { poiModel -> markerBuilder.buildMarker(poiModel) }
                }
                .map { createMarkersHashMap(ResourceTypes.POI, it) }
    }

    private fun prepareChargerObservable(): Observable<HashMap<MarkersGroup, List<Marker>>> {
        return getChargers.execute()
                .map {
                    it.map { chargerModel -> markerBuilder.buildMarker(chargerModel) }
                }
                .map { createMarkersHashMap(ResourceTypes.CHARGER, it) }

    }

    private fun prepareParkingObservable(): Observable<HashMap<MarkersGroup, List<Marker>>> {
        return getParkings.execute()
                .map {
                    it.map { parkingModel -> markerBuilder.buildMarker(parkingModel) }
                }
                .map { createMarkersHashMap(ResourceTypes.PARKING, it) }
    }


    private fun prepareVehicleObservable(): Observable<HashMap<MarkersGroup, List<Marker>>> {
        return getVehicles.execute()
                .map {
                    it.map { vehicleModel -> markerBuilder.buildMarker(vehicleModel) }
                }
                .map { createMarkersHashMap(ResourceTypes.VEHICLE, it) }
    }
}