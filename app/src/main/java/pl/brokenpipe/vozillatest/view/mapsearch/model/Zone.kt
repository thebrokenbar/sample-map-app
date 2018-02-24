package pl.brokenpipe.vozillatest.view.mapsearch.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class Zone(
        val polygon: List<LatLng>,
        val fillColor: MapColor,
        val stokeColor: MapColor
)