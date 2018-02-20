package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.mapsearch.GoogleMapView
import pl.brokenpipe.vozillatest.mapsearch.arch.MapView

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Module
class MapsActivityModule(private val mapsActivity: MapsActivity) {
    @Provides
    fun provideMapView(): MapView {
        return GoogleMapView(mapsActivity)
    }
}