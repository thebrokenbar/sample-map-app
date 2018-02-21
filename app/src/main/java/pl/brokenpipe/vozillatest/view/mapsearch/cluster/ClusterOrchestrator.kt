package pl.brokenpipe.vozillatest.view.mapsearch.cluster

import android.content.Context
import android.os.Build
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import java.lang.ref.WeakReference


/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class ClusterOrchestrator(context: Context,
                          private val googleMap: GoogleMap,
                          private val iconGenerator: IconGenerator
) : GoogleMap.OnCameraIdleListener {

    private val contextRef = WeakReference(context)

    private val clusterManagers: HashMap<MarkersGroup, ClusterManager<Marker>> = hashMapOf()

    fun initialize(configs: List<MarkersGroup>) {
        val context = contextRef.get() ?: throw IllegalStateException("Context is lost")

        configs.forEach {
            clusterManagers[it] = ClusterManager<Marker>(context, googleMap)
                    .apply {
                        val clusterColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            context.getColor(it.clusterColor.colorResId)
                        } else {
                            @Suppress("DEPRECATION")
                            context.resources.getColor(it.clusterColor.colorResId)
                        }
                        renderer = ClusterRenderer(context, googleMap, this,
                                iconGenerator, clusterColor)
                    }
        }
    }

    fun clusterAll() {
        clusterManagers.forEach {
            it.value.cluster()
        }
    }

    fun cluster(markersGroup: MarkersGroup) {
        clusterManagers[markersGroup]?.cluster()
                ?: throw IllegalArgumentException("No markersGroup of id $markersGroup")
    }

    fun add(markersGroup: MarkersGroup, marker: Marker) {
        clusterManagers[markersGroup]?.addItem(marker)
                ?: throw IllegalArgumentException("No markersGroup of id $markersGroup")
    }

    override fun onCameraIdle() {
        clusterManagers.forEach {
            it.value.onCameraIdle()
        }
    }

    fun clearAllMarkers() {
        clusterManagers.forEach {
            it.value.clearItems()
        }
    }

    fun clearMarkers(markersGroup: MarkersGroup) {
        clusterManagers[markersGroup]?.clearItems()
                ?: throw IllegalArgumentException("No markersGroup of id $markersGroup")
    }

}