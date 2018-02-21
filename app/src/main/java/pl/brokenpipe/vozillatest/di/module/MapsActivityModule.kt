package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.arch.Arch
import pl.brokenpipe.vozillatest.di.scope.ActivityScope
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.interactor.model.MapObject
import pl.brokenpipe.vozillatest.mapsearch.GoogleMapView
import pl.brokenpipe.vozillatest.mapsearch.MapSearchViewModel
import pl.brokenpipe.vozillatest.mapsearch.arch.MapView

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Module
class MapsActivityModule(private val mapsActivity: MapsActivity) {
    @Provides
    @ActivityScope
    fun provideMapView(getClusterTypes: Arch.UseCase<Unit, List<ClusterType>>,
                       getMapObjects: Arch.UseCase<List<String>, Map<ClusterType, List<MapObject>>>
    ): MapView {
        return GoogleMapView(mapsActivity, MapSearchViewModel(getClusterTypes, getMapObjects))
    }
}