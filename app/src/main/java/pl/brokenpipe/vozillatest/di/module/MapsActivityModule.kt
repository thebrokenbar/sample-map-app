package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.arch.mapsearch.MapView
import pl.brokenpipe.vozillatest.di.scope.ActivityScope
import pl.brokenpipe.vozillatest.view.MapsActivity
import pl.brokenpipe.vozillatest.view.mapsearch.MapSearchView

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Module
class MapsActivityModule(private val mapsActivity: MapsActivity) {
    @Provides
    @ActivityScope
    fun provideMapView(mapSearchViewModel: MapSearchPresenter): MapView {
        return MapSearchView(mapsActivity, mapSearchViewModel)
    }
}