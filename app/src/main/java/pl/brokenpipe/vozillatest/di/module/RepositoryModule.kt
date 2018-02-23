package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import io.swagger.client.api.MapApi
import io.swagger.client.model.MapSearchResponse
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.repository.map.MapObjectsRepository
import pl.brokenpipe.vozillatest.repository.map.specification.MapSpecification
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMapObjectsRepository(mapApi: MapApi): Repository<@JvmWildcard  MapSearchResponse, @JvmWildcard MapSpecification> {
        return MapObjectsRepository(mapApi)
    }
}