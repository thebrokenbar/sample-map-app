package pl.brokenpipe.vozillatest.view.mapsearch.model

import android.support.annotation.ColorRes
import com.google.android.gms.maps.model.LatLng

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class Zone(
        val polygon: List<LatLng>,
        @ColorRes val fillColor: Int,
        @ColorRes val stokeColor: Int
)