package pl.brokenpipe.vozillatest.presenter.mapsearch

import com.google.android.gms.maps.model.LatLng
import pl.brokenpipe.vozillatest.interactor.model.Point
import pl.brokenpipe.vozillatest.interactor.model.ZoneModel
import pl.brokenpipe.vozillatest.view.mapsearch.model.MapColor
import pl.brokenpipe.vozillatest.view.mapsearch.model.Zone

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
class ZoneBuilder {

    fun buildZones(zoneModel: ZoneModel): MutableList<Zone> {
        val zones = mutableListOf<Zone>()
        zones.addAll(zoneModel.allowedAreaPolygonPoints.map { polygon ->
            Zone(polygon.map { buildLatLng(it) }, MapColor.Transparent(), MapColor.BlueColor())
        })
        zones.addAll(zoneModel.excludedAreaPolygonPoints.map { polygon ->
            Zone(polygon.map { buildLatLng(it) }, MapColor.BrownTransparentColor(), MapColor.RedColor())
        })

        return zones
    }

    private fun buildLatLng(point: Point): LatLng {
        return LatLng(point.lat, point.lon)
    }
}