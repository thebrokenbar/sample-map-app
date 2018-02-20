package pl.brokenpipe.vozillatest.mapsearch.cluster.clusterrenderer

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.mapsearch.model.VehicleMarker

class VehicleClusterRenderer(context: Context,
                             googleMap: GoogleMap,
                             vehicleClusterManager: ClusterManager<VehicleMarker>,
                             iconGenerator: IconGenerator
) : BaseClusterRenderer<VehicleMarker>(context, googleMap, vehicleClusterManager, iconGenerator) {

    override fun createIcon(marker: VehicleMarker, iconGenerator: IconGenerator): Bitmap {
        return iconGenerator.makeIcon()
    }
}