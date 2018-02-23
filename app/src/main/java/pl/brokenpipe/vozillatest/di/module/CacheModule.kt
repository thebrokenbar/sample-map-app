package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.interactor.model.*
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideParkingCache(): HashMap<@JvmWildcard String, @JvmWildcard ParkingModel> = hashMapOf()

    @Provides
    @Singleton
    fun provideVehicleCache(): HashMap<@JvmWildcard String, @JvmWildcard VehicleModel> = hashMapOf()

    @Provides
    @Singleton
    fun provideChargerCache(): HashMap<@JvmWildcard String, @JvmWildcard ChargerModel> = hashMapOf()

    @Provides
    @Singleton
    fun providePoiCache(): HashMap<@JvmWildcard String, @JvmWildcard PoiModel> = hashMapOf()

    @Provides
    @Singleton
    fun provideZoneCache(): HashMap<@JvmWildcard String, @JvmWildcard ZoneModel> = hashMapOf()


}