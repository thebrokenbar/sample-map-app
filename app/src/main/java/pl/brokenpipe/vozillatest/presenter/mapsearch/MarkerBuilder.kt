package pl.brokenpipe.vozillatest.presenter.mapsearch

import com.google.android.gms.maps.model.LatLng
import pl.brokenpipe.vozillatest.interactor.model.*
import pl.brokenpipe.vozillatest.view.mapsearch.model.MapColor
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkerInfo
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
class MarkerBuilder {
    fun buildMarker(chargerModel: ChargerModel): Marker {
        return Marker(
                LatLng(chargerModel.location.lat, chargerModel.location.lon),
                chargerModel.name,
                chargerModel.address,
                MapColor.YellowColor()
        )
    }

    fun buildMarker(poiModel: PoiModel): Marker {
        return Marker(
                LatLng(poiModel.location.lat, poiModel.location.lon),
                poiModel.name,
                "",
                MapColor.OrangeColor()
        )
    }

    fun buildMarker(parkingModel: ParkingModel): Marker {
        return Marker(
                LatLng(parkingModel.location.lat, parkingModel.location.lon),
                "${parkingModel.availableSpacesCount}/${parkingModel.spacesCount}",
                parkingModel.address,
                MapColor.BlueColor()
        )
    }

    fun buildMarker(vehicleModel: VehicleModel): Marker {

        return Marker(
                LatLng(vehicleModel.location.lat, vehicleModel.location.lon),
                vehicleModel.name,
                vehicleModel.rangeKm.toString(),
                getStatusColor(vehicleModel.status),
                MarkerInfo.VehicleInfo(
                        vehicleModel.rangeKm.toString(),
                        vehicleModel.name,
                        vehicleModel.platesNumber,
                        formatDate(vehicleModel.reservationEnd),
                        vehicleModel.batteryLevelPct,
                        vehicleModel.picture
                )
        )
    }

    private fun formatDate(date: Date?): String {
        return date?.let { SimpleDateFormat.getDateTimeInstance().format(it) } ?: ""
    }

    private fun getStatusColor(status: VehicleStatus): MapColor {
        return when (status) {
            is VehicleStatus.Available -> MapColor.GreenColor()
            else -> MapColor.RedColor()
        }
    }
}