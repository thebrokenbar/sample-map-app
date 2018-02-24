package pl.brokenpipe.vozillatest.view.mapsearch.mapelements

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.SparseArray
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.view.extension.intColor
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import java.lang.ref.WeakReference

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class ClusterRenderer(
        context: Context,
        googleMap: GoogleMap,
        clusterManager: ClusterManager<Marker>,
        private val iconGenerator: IconGenerator,
        private val clusterTintColor: Int = 0xFFFFFFFF.toInt()
) : DefaultClusterRenderer<Marker>(context, googleMap, clusterManager) {

    private val contextRef = WeakReference(context)

    private val clusterDrawable: Drawable = makeClusterDrawable()

    private var clusterIconsCache = SparseArray<Bitmap>()

    private var markerIconsCache = HashMap<String, Bitmap>()

    private fun makeClusterDrawable(): ShapeDrawable {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = clusterTintColor
        return drawable
    }

    override fun onBeforeClusterItemRendered(item: Marker, markerOptions: MarkerOptions) {
        val icon = markerIconsCache[item.name]
                ?: iconGenerator
                        .apply { setColor(item.color.intColor(contextRef.get())) }
                        .makeIcon(item.name)
                        .apply { markerIconsCache["${item.color.colorResId}${item.name}"] = this }

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Marker>, markerOptions: MarkerOptions) {
        val icon = clusterIconsCache[cluster.size] ?: let {
            val wrappedDrawable = DrawableCompat
                    .wrap(clusterDrawable)
                    .mutate()

            clusterTintColor.let { DrawableCompat.setTint(wrappedDrawable, it) }
            iconGenerator.setBackground(wrappedDrawable)
            return@let iconGenerator.makeIcon("${cluster.size}").also {
                clusterIconsCache.put(cluster.size, it)
            }
        }

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).zIndex(10f)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Marker>): Boolean {
        return cluster.size > 1
    }
}