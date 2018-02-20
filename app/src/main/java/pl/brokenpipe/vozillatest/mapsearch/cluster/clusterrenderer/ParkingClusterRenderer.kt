package pl.brokenpipe.vozillatest.mapsearch.cluster.clusterrenderer

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.mapsearch.model.ParkingMarker

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class ParkingClusterRenderer(context: Context,
                             googleMap: GoogleMap,
                             parkingClusterManager: ClusterManager<ParkingMarker>,
                             iconGenerator: IconGenerator
) : BaseClusterRenderer<ParkingMarker>(context, googleMap, parkingClusterManager, iconGenerator) {
    override fun createIcon(marker: ParkingMarker, iconGenerator: IconGenerator): Bitmap {
        return iconGenerator.makeIcon(marker.freeSlots.toString())
    }


}