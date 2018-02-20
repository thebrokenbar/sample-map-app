package pl.brokenpipe.vozillatest.mapsearch.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
abstract class Marker: ClusterItem {

    abstract val pos: LatLng
    abstract val name: String
    abstract val description: String
    abstract val color: MapColor


    override fun getSnippet(): String {
        return description
    }

    override fun getTitle(): String {
        return name
    }

    override fun getPosition(): LatLng {
        return pos
    }
}