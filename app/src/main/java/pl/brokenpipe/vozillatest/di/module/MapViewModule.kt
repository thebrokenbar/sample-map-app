package pl.brokenpipe.vozillatest.di.module

import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.ui.IconGenerator
import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.view.MapsActivity
import pl.brokenpipe.vozillatest.di.scope.ViewScope
import pl.brokenpipe.vozillatest.view.mapsearch.mapelements.ClusterOrchestrator
import pl.brokenpipe.vozillatest.view.mapsearch.mapelements.ZoneOrchestrator

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Module
class MapViewModule(private val googleMap: GoogleMap) {

    @Provides
    @ViewScope
    fun provideIconGenerator(activity: MapsActivity): IconGenerator {
        return IconGenerator(activity)
    }

    @Provides
    fun provideClusterOrchestrator(activity: MapsActivity, iconGenerator: IconGenerator
    ): ClusterOrchestrator {
        return ClusterOrchestrator(activity, googleMap, iconGenerator)

    }

    @Provides
    fun provideZoneOrchestrator(activity: MapsActivity): ZoneOrchestrator {
        return ZoneOrchestrator(activity, googleMap)
    }
}