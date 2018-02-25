package pl.brokenpipe.vozillatest.presenter.mapsearch

import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.constant.ResourceTypes
import pl.brokenpipe.vozillatest.interactor.model.*
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
import pl.brokenpipe.vozillatest.view.mapsearch.model.Zone

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
@RunWith(MockitoJUnitRunner::class)
class MapSearchViewModelTest {

    private lateinit var mapSearchViewModel: MapSearchViewModel

    @Mock
    lateinit var getClusterTypes: UseCase<Unit, List<ClusterType>>

    @Mock
    lateinit var refreshMapObjects: UseCase<MapObjectsFilter, Unit>

    @Mock
    lateinit var getVehicles: UseCase<String, List<VehicleModel>>

    @Mock
    lateinit var getParkings: UseCase<String, List<ParkingModel>>

    @Mock
    lateinit var getChargers: UseCase<String, List<ChargerModel>>

    @Mock
    lateinit var getPois: UseCase<String, List<PoiModel>>

    @Mock
    lateinit var getZones: UseCase<String, List<ZoneModel>>

    @Mock
    lateinit var markerBuilder: MarkerBuilder

    @Mock
    lateinit var zoneBuilder: ZoneBuilder

    @Mock
    lateinit var getFilterModels: UseCase<Unit, List<Pair<String, String>>>

    @Mock
    lateinit var getFilterStatuses: UseCase<Unit, List<Pair<String, String>>>

    @Before
    fun setUp() {
        mapSearchViewModel = MapSearchViewModel(getClusterTypes, refreshMapObjects, getVehicles, getParkings,
                getChargers, getPois, getZones, markerBuilder, zoneBuilder, getFilterModels, getFilterStatuses)
    }

    @Test
    fun `return adequate markers groups when queried for cluster types`() {
        //given
        val clusterTypes = listOf(ClusterType("clusterId", "any Label", "not null options"))
        whenever(getClusterTypes.execute()).thenReturn(Observable.just(clusterTypes))

        //when
        val test = mapSearchViewModel.getMarkersGroup().test()

        //then

        test.assertComplete()
        test.assertValue {
            it.size == clusterTypes.size && it[0].id == clusterTypes[0].id && it[0].options == clusterTypes[0].options
        }
    }

    @Test
    fun `trigger search filter change after manual update`() {
        //given

        //when
        val test = mapSearchViewModel.observeSearchFilterChanges().test()
        mapSearchViewModel.manualRefresh().subscribe()

        //then
        test.assertValueCount(1)
    }

    @Test
    fun `return empty markers when search filter is empty`() {
        //given
        whenever(refreshMapObjects.execute(argThat {
            !(getVehicles || getParkings || getPois || getChargers || getZones)
        })).thenReturn(Observable.just(Unit))
        val searchFilter = SearchFilter(emptyList())
        mapSearchViewModel.setSearchFilter(searchFilter).subscribe()

        //when
        val test = mapSearchViewModel.fetchMarkers().test()
        //then
        test.assertValue {
            it.all { it.value.isEmpty() }
        }
    }

    @Test
    fun `throw exception when fetching for markers with unexpected data from api`() {
        //given
        val objectTypes = listOf(ResourceTypes.VEHICLE, ResourceTypes.PARKING, ResourceTypes.POI,
                ResourceTypes.CHARGER, ResourceTypes.ZONE)
        val clusterTypes = listOf(ClusterType("unexpected", "unexpected"))
        whenever(getClusterTypes.execute()).thenReturn(Observable.just(clusterTypes))
        val searchFilter = SearchFilter(objectTypes)
        val vehicleModel: VehicleModel = mock()
        val parkingModel: ParkingModel = mock()
        val chargerModel: ChargerModel = mock()
        val poiModel: PoiModel = mock()
        val zoneModel: ZoneModel = mock()
        whenever(getVehicles.execute()).thenReturn(Observable.just(listOf(vehicleModel)))
        whenever(getParkings.execute()).thenReturn(Observable.just(listOf(parkingModel)))
        whenever(getChargers.execute()).thenReturn(Observable.just(listOf(chargerModel)))
        whenever(getPois.execute()).thenReturn(Observable.just(listOf(poiModel)))
        whenever(getZones.execute()).thenReturn(Observable.just(listOf(zoneModel)))
        whenever(refreshMapObjects.execute(argThat {
            getVehicles && getParkings && getChargers && getPois && getZones
        })).thenReturn(Observable.just(Unit))

        mapSearchViewModel.setSearchFilter(searchFilter).subscribe()
        mapSearchViewModel.getMarkersGroup().subscribe()

        //when
        val test = mapSearchViewModel.fetchMarkers().test()

        //then
        test.assertError(IllegalStateException::class.java)

    }

    @Test
    fun `return markers corresponding to search filter`() {
        //given
        val objectTypes = listOf(ResourceTypes.VEHICLE, ResourceTypes.PARKING, ResourceTypes.POI,
                ResourceTypes.CHARGER, ResourceTypes.ZONE)
        val searchFilter = SearchFilter(objectTypes)
        whenever(refreshMapObjects.execute(argThat {
            getVehicles && getParkings && getChargers && getPois && getZones
        })).thenReturn(Observable.just(Unit))
        val vehicleModel: VehicleModel = mock()
        val parkingModel: ParkingModel = mock()
        val chargerModel: ChargerModel = mock()
        val poiModel: PoiModel = mock()
        val zoneModel: ZoneModel = mock()
        whenever(getVehicles.execute()).thenReturn(Observable.just(listOf(vehicleModel)))
        whenever(getParkings.execute()).thenReturn(Observable.just(listOf(parkingModel)))
        whenever(getChargers.execute()).thenReturn(Observable.just(listOf(chargerModel)))
        whenever(getPois.execute()).thenReturn(Observable.just(listOf(poiModel)))
        whenever(getZones.execute()).thenReturn(Observable.just(listOf(zoneModel)))
        val clusterTypes = objectTypes.map { ClusterType(it, "any") }
        whenever(getClusterTypes.execute()).thenReturn(Observable.just(clusterTypes))
        val vehicleMarker: Marker = mock()
        val parkingMarker: Marker = mock()
        val chargerMarker: Marker = mock()
        val poiMarker: Marker = mock()
        whenever(markerBuilder.buildMarker(vehicleModel)).thenReturn(vehicleMarker)
        whenever(markerBuilder.buildMarker(parkingModel)).thenReturn(parkingMarker)
        whenever(markerBuilder.buildMarker(chargerModel)).thenReturn(chargerMarker)
        whenever(markerBuilder.buildMarker(poiModel)).thenReturn(poiMarker)

        mapSearchViewModel.setSearchFilter(searchFilter).subscribe()
        mapSearchViewModel.getMarkersGroup().subscribe()

        //when
        val test = mapSearchViewModel.fetchMarkers().test()

        //then
        test.assertValue {
            val vehicleMarkersKey = it.keys.find { it.id == ResourceTypes.VEHICLE }
            val vehicle = it[vehicleMarkersKey]?.get(0) == vehicleMarker

            val parkingsMarkersKey = it.keys.find { it.id == ResourceTypes.PARKING }
            val parking = it[parkingsMarkersKey]?.get(0) == parkingMarker

            val chargersMarkersKey = it.keys.find { it.id == ResourceTypes.CHARGER }
            val charger = it[chargersMarkersKey]?.get(0) == chargerMarker

            val poisMarkersKey = it.keys.find { it.id == ResourceTypes.POI }
            val poi = it[poisMarkersKey]?.get(0) == poiMarker

            return@assertValue vehicle && parking && charger && poi
        }
    }

    @Test
    fun `return zones when requested with search filter`() {
        //given
        val zoneModel: ZoneModel = mock()
        val zone: Zone = mock()
        whenever(getZones.execute()).thenReturn(Observable.just(listOf(zoneModel)))
        whenever(zoneBuilder.buildZones(zoneModel)).thenReturn(mutableListOf(zone))

        //when
        val test = mapSearchViewModel.getZones().test()

        //then
        test.assertValue {
            it[0] == zone
        }
    }

    @Test
    fun `return filter items for vehicle model`() {
        //given
        val pair = Pair("modelId", "modelLabel")
        whenever(getFilterModels.execute()).thenReturn(Observable.just(listOf(pair)))
        //when
        val test = mapSearchViewModel.fetchVehicleModelsToFilter().test()

        //then
        test.assertValue {
            it[0].id == pair.first && it[0].label == pair.second
        }
    }

    @Test
    fun `return filter items for vehicle status`() {
        //given
        val pair = Pair("statusId", "statusLabel")
        whenever(getFilterStatuses.execute()).thenReturn(Observable.just(listOf(pair)))
        //when
        val test = mapSearchViewModel.fetchVehicleStatusesToFilter().test()

        //then
        test.assertValue {
            it[0].id == pair.first && it[0].label == pair.second
        }
    }

    @Test
    fun `return search filter immediately when exists`() {
        //given
        val objectTypes = listOf(ResourceTypes.VEHICLE, ResourceTypes.PARKING, ResourceTypes.POI,
                ResourceTypes.CHARGER, ResourceTypes.ZONE)
        val searchFilter = SearchFilter(objectTypes)

        mapSearchViewModel.setSearchFilter(searchFilter).subscribe()

        //when
        val test = mapSearchViewModel.getSearchFilterImmediately().test()

        //then
        test.assertValue(searchFilter)
    }

}