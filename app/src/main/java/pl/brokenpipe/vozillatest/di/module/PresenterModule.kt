package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.interactor.model.*
import pl.brokenpipe.vozillatest.presenter.mapsearch.MapSearchViewModel
import pl.brokenpipe.vozillatest.presenter.mapsearch.MarkerBuilder
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
@Module
class PresenterModule {
    @Provides
    @Singleton
    fun provideMarkerBuilder() = MarkerBuilder()

    @Provides
    fun provideMapSearchPresenter(
            getClusterTypes: UseCase<Unit, List<ClusterType>>,
            refreshMapObjects: UseCase<MapObjectsFilter, Unit>,
            getVehicles: UseCase<String, List<VehicleModel>>,
            getParkings: UseCase<String, List<ParkingModel>>,
            getChargers: UseCase<String, List<ChargerModel>>,
            getPois: UseCase<String, List<PoiModel>>,
            getZones: UseCase<String, List<ZoneModel>>,
            markerBuilder: MarkerBuilder
    ): MapSearchPresenter {
        return MapSearchViewModel(getClusterTypes, refreshMapObjects,
                getVehicles, getParkings, getChargers, getPois, getZones, markerBuilder)
    }
}