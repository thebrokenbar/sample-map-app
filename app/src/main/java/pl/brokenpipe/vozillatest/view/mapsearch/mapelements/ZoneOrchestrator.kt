package pl.brokenpipe.vozillatest.view.mapsearch.mapelements

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolygonOptions
import pl.brokenpipe.vozillatest.view.extension.intColor
import pl.brokenpipe.vozillatest.view.mapsearch.model.Zone
import java.lang.ref.WeakReference

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
class ZoneOrchestrator(context: Context, private val googleMap: GoogleMap) {
    private val contextRef = WeakReference(context)

    fun add(zone: Zone) {
        googleMap.addPolygon(PolygonOptions().apply {
            strokeColor(zone.stokeColor.intColor(contextRef.get()))
            fillColor(zone.fillColor.intColor(contextRef.get()))
            addAll(zone.polygon)
        })
    }
}