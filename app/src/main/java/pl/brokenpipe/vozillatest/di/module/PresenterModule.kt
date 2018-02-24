package pl.brokenpipe.vozillatest.di.module

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.arch.ViewModelFactory
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.di.scope.ActivityScope
import pl.brokenpipe.vozillatest.interactor.model.*
import pl.brokenpipe.vozillatest.presenter.mapsearch.MapSearchViewModel
import pl.brokenpipe.vozillatest.presenter.mapsearch.MarkerBuilder
import pl.brokenpipe.vozillatest.view.MapsActivity
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
@Module
class PresenterModule {

    @Provides
    @ActivityScope
    fun provideMarkerBuilder() = MarkerBuilder()

    @Provides
    fun provideMapSearchPresenter(
            activity: MapsActivity,
            @Named("MapSearchPresenter") factory: ViewModelProvider.Factory
    ): MapSearchPresenter {
        return ViewModelProviders.of(activity, factory)[MapSearchViewModel::class.java]
    }

    @Provides
    @ActivityScope
    @Named("MapSearchPresenter")
    fun provideMapSearchViewModelFactory(
            getClusterTypes: UseCase<Unit, List<ClusterType>>,
            refreshMapObjects: UseCase<MapObjectsFilter, Unit>,
            getVehicles: UseCase<String, List<VehicleModel>>,
            getParkings: UseCase<String, List<ParkingModel>>,
            getChargers: UseCase<String, List<ChargerModel>>,
            getPois: UseCase<String, List<PoiModel>>,
            getZones: UseCase<String, List<ZoneModel>>,
            markerBuilder: MarkerBuilder): ViewModelProvider.Factory {

        return ViewModelFactory {
            MapSearchViewModel(getClusterTypes, refreshMapObjects, getVehicles, getParkings,
                    getChargers, getPois, getZones, markerBuilder)
        }

    }
}