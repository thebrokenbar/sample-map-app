package pl.brokenpipe.vozillatest.view.mapsearch.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
data class Marker(
        private val pos: LatLng,
        val name: String,
        val description: String,
        val color: MapColor,
        val markerInfo: MarkerInfo? = null) : ClusterItem {

    override fun getSnippet(): String? {
        return null
    }

    override fun getTitle(): String? {
        return if (description.isBlank()) null else description
    }

    override fun getPosition(): LatLng {
        return pos
    }
}