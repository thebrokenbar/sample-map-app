package pl.brokenpipe.vozillatest.mapsearch.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class VehicleMarker(
        override val pos: LatLng,
        override val name: String,
        override val description: String,
        override val color: MapColor,
        val vehicleId: String
) : Marker()