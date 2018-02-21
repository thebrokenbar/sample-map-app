package pl.brokenpipe.vozillatest.mapsearch.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class Marker(
        val objectId: String,
        private val pos: LatLng,
        val name: String,
        val description: String,
        val color: MapColor): ClusterItem {

    override fun getSnippet(): String {
        return description
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getPosition(): LatLng {
        return pos
    }
}