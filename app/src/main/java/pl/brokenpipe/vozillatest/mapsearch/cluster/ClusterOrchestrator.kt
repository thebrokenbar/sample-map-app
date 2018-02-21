package pl.brokenpipe.vozillatest.mapsearch.cluster

import android.content.Context
import android.os.Build
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.mapsearch.model.Marker
import java.lang.ref.WeakReference


/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class ClusterOrchestrator(context: Context,
                          private val googleMap: GoogleMap,
                          private val iconGenerator: IconGenerator
) : GoogleMap.OnCameraIdleListener {

    private val contextRef = WeakReference(context)

    private val clusterManagers: HashMap<Cluster, ClusterManager<Marker>> = hashMapOf()

    fun initialize(configs: List<Cluster>) {
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

    fun cluster(cluster: Cluster) {
        clusterManagers[cluster]?.cluster()
                ?: throw IllegalArgumentException("No cluster of id $cluster")
    }

    fun add(cluster: Cluster, marker: Marker) {
        clusterManagers[cluster]?.addItem(marker)
                ?: throw IllegalArgumentException("No cluster of id $cluster")
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

    fun clearMarkers(cluster: Cluster) {
        clusterManagers[cluster]?.clearItems()
                ?: throw IllegalArgumentException("No cluster of id $cluster")
    }

}