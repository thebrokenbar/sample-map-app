package pl.brokenpipe.vozillatest.mapsearch.cluster.clusterrenderer

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.mapsearch.model.Marker
import java.lang.ref.WeakReference

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
abstract class BaseClusterRenderer<T : Marker>(
        context: Context,
        googleMap: GoogleMap,
        clusterManager: ClusterManager<T>,
        private val iconGenerator: IconGenerator
) : DefaultClusterRenderer<T>(context, googleMap, clusterManager) {

    private val contextRef = WeakReference(context)

    abstract fun createIcon(marker: T, iconGenerator: IconGenerator): Bitmap

    override fun onBeforeClusterItemRendered(item: T, markerOptions: MarkerOptions) {
        iconGenerator.setColor(getColorFromMarker(item))

        markerOptions
                .icon(BitmapDescriptorFactory.fromBitmap(createIcon(item, iconGenerator)))
                .title(item.name)
    }

    private fun getColorFromMarker(item: T): Int {
        return contextRef.get()?.let {
            return@let if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                contextRef.get()?.getColor(item.color.colorResId)
            } else {
                @Suppress("DEPRECATION")
                contextRef.get()?.resources?.getColor(item.color.colorResId)
            }
        } ?: throw IllegalStateException("Context is lost")
    }

    override fun shouldRenderAsCluster(cluster: Cluster<T>): Boolean {
        return cluster.size > 1
    }
}