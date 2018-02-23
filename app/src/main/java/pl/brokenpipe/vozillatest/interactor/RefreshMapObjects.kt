package pl.brokenpipe.vozillatest.interactor

import io.reactivex.Observable
import io.swagger.client.BuildConfig
import io.swagger.client.model.*
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.interactor.model.*
import pl.brokenpipe.vozillatest.constant.ResourceTypes
import pl.brokenpipe.vozillatest.repository.map.specification.MapSpecification
import java.util.*

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
class RefreshMapObjects(
        private val mapObjectsRepository: Repository<MapSearchResponse, MapSpecification>,
        private val vehicleCache: HashMap<String, VehicleModel>,
        private val parkingCache: HashMap<String, ParkingModel>,
        private val chargerCache: HashMap<String, ChargerModel>,
        private val poiCache: HashMap<String, PoiModel>,
        private val zoneCache: HashMap<String, ZoneModel>

) : UseCase<MapObjectsFilter, Unit> {
    override fun execute(param: MapObjectsFilter?): Observable<Unit> {
        return Observable.defer {
            param ?: throw IllegalArgumentException("Param can't be null")

            clearCache()

            return@defer mapObjectsRepository.get(buildSpecification(param))
                    .firstOrError()
                    .doOnSuccess {
                        buildVehicleCache(it.vehicles.orEmpty())
                        buildParkingCache(it.parking.orEmpty())
                        buildChargerCache(it.chargers.orEmpty())
                        buildPoiCache(it.poi.orEmpty())
                        buildZoneCache(it.zones.orEmpty())
                    }
                    .map { Unit }
                    .toObservable()
        }
    }

    private fun buildZoneCache(zones: List<Zone>) {
        zones.forEach { zone ->
            val allowedAreas = zone.allowedAreas.map { area ->
                area.points.map { buildPoint(it) }
            }
            val excludedAreas = zone.excludedAreas.map { area ->
                area.points.map { buildPoint(it) }
            }
            val zoneModel = ZoneModel(
                    zone.id.toString(),
                    zone.name,
                    allowedAreas,
                    excludedAreas
            )
            zoneCache[zoneModel.id] = zoneModel
        }
    }

    private fun buildPoiCache(pois: List<POI>) {
        pois.forEach { poi ->
            val poiModel = PoiModel(
                    poi.id.toString(),
                    buildPoint(poi.location),
                    poi.name
            )
            poiCache[poiModel.id] = poiModel
        }
    }

    private fun buildChargerCache(chargers: List<Charger>) {
        chargers.forEach { charger ->
            val chargerModel = ChargerModel(
                    charger.id.toString(),
                    buildAddress(charger.address),
                    charger.parkingId?.toString(),
                    charger.type.model,
                    buildPoint(charger.location),
                    charger.name
            )
            chargerCache[chargerModel.id] = chargerModel
        }
    }


    private fun buildParkingCache(parkings: List<Parking>) {
        parkings.forEach { parking ->
            val parkingModel = ParkingModel(
                    parking.id.toString(),
                    parking.spacesCount,
                    parking.availableSpacesCount,
                    buildPoint(parking.location),
                    buildAddress(parking.address)
            )
            parkingCache[parkingModel.id] = parkingModel
        }
    }

    private fun buildVehicleCache(vehicles: List<Vehicle>) {
        vehicles.forEach { vehicle ->
            val pictureUrl = "${BuildConfig.HOST}\\${vehicle.picture.id}"
            val vehicleModel = VehicleModel(
                    vehicle.id.toString(),
                    vehicle.rangeKm, vehicle.name, vehicle.platesNumber,
                    vehicle.reservationEnd?.let { Date(it.toEpochSecond() * 1000) },
                    vehicle.batteryLevelPct, pictureUrl, vehicle.color,
                    buildPoint(vehicle.location),
                    buildVehicleStatus(vehicle.status),
                    vehicle.sideNumber
            )
            vehicleCache[vehicleModel.id] = vehicleModel
        }
    }

    private fun buildSpecification(mapObjectsFilter: MapObjectsFilter): MapSpecification {
        val objectType = mutableListOf<String>()
        val vehicleModel = mutableListOf<String>()
        val vehicleStatus = mutableListOf<String>()

        if (mapObjectsFilter.getParkings) {
            objectType.add(ResourceTypes.PARKING)
        }
        if (mapObjectsFilter.getChargers) {
            objectType.add(ResourceTypes.CHARGER)
        }
        if (mapObjectsFilter.getPois) {
            objectType.add(ResourceTypes.POI)
        }
        if (mapObjectsFilter.getZones) {
            objectType.add(ResourceTypes.ZONE)
        }
        if (mapObjectsFilter.getVehicles) {
            objectType.add(ResourceTypes.VEHICLE)
            mapObjectsFilter.vehiclesFilter?.model?.let { vehicleModel.add(it) }
            mapObjectsFilter.vehiclesFilter?.status?.let { vehicleStatus.add(it) }
        }

        return MapSpecification(
                objectType = objectType,
                vehicleModel = vehicleModel,
                vehicleStatus = vehicleStatus
        )
    }

    private fun clearCache() {
        vehicleCache.clear()
        parkingCache.clear()
        chargerCache.clear()
        poiCache.clear()
        zoneCache.clear()
    }

    private fun buildPoint(geoPoint: GeoPoint): Point {
        return Point(geoPoint.latitude, geoPoint.longitude)
    }

    private fun buildAddress(mapAddress: MapAddress?): String {
        return "${mapAddress?.city} ${mapAddress?.street} ${mapAddress?.house}"
    }

    private fun buildVehicleStatus(status: Vehicle.StatusEnum): VehicleStatus {
        return when(status) {
            Vehicle.StatusEnum.AVAILABLE -> VehicleStatus.Available()
            Vehicle.StatusEnum.RENTED -> VehicleStatus.Rented()
            Vehicle.StatusEnum.RESERVED -> VehicleStatus.Reserved()
            Vehicle.StatusEnum.RETURNED -> VehicleStatus.Returned()
            Vehicle.StatusEnum.UNAVAILABLE -> VehicleStatus.Unavailable()
        }
    }

}