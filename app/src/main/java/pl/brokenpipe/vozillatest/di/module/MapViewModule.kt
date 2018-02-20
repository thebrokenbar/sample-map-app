package pl.brokenpipe.vozillatest.di.module

import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.mapsearch.cluster.clusterrenderer.ParkingClusterRenderer
import pl.brokenpipe.vozillatest.mapsearch.cluster.clusterrenderer.VehicleClusterRenderer
import pl.brokenpipe.vozillatest.mapsearch.model.ParkingMarker
import pl.brokenpipe.vozillatest.mapsearch.model.VehicleMarker
import java.lang.ref.WeakReference

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Module
class MapViewModule(private val activityRef: WeakReference<MapsActivity>,
                    private val googleMap: GoogleMap) {

    private val activity: MapsActivity
        get() = activityRef.get() ?: throw IllegalStateException("Context is lost")

    @Provides
    fun provideIconGenerator(): IconGenerator {
        return IconGenerator(activity)
    }

    @Provides
    fun provideParkingClusterManager(): ClusterManager<ParkingMarker> {
        return ClusterManager(activity, googleMap)
    }

    @Provides
    fun provideVehicleClusterManager(): ClusterManager<VehicleMarker> {
        return ClusterManager(activity, googleMap)
    }

    @Provides
    fun provideParkingClusterRenderer(
                                      vehicleClusterManager: ClusterManager<ParkingMarker>,
                                      iconGenerator: IconGenerator): DefaultClusterRenderer<ParkingMarker> {

        return ParkingClusterRenderer(activity, googleMap, vehicleClusterManager, iconGenerator)
    }

    @Provides
    fun provideVehicleClusterRenderer(
                                      vehicleClusterManager: ClusterManager<VehicleMarker>,
                                      iconGenerator: IconGenerator): DefaultClusterRenderer<VehicleMarker> {

        return VehicleClusterRenderer(activity, googleMap, vehicleClusterManager, iconGenerator)
    }
}