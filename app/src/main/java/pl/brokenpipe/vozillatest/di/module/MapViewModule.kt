package pl.brokenpipe.vozillatest.di.module

import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.ui.IconGenerator
import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.di.scope.ViewScope
import pl.brokenpipe.vozillatest.mapsearch.cluster.ClusterOrchestrator
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
    @ViewScope
    fun provideIconGenerator(): IconGenerator {
        return IconGenerator(activity)
    }

    @Provides
    fun provideClusterOrchestrator(iconGenerator: IconGenerator): ClusterOrchestrator {
        return ClusterOrchestrator(activity, googleMap, iconGenerator)

    }
}