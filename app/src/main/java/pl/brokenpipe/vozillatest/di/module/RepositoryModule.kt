package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import io.swagger.client.api.MapApi
import io.swagger.client.model.MapSearchResponse
import pl.brokenpipe.vozillatest.arch.Repository
import pl.brokenpipe.vozillatest.repository.map.MapObjectsRepository
import pl.brokenpipe.vozillatest.repository.map.specification.MapSpecification
import pl.brokenpipe.vozillatest.repository.mapfilter.MapFiltersRepository
import pl.brokenpipe.vozillatest.repository.mapfilter.specification.MapFiltersSpecification
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 23.02.2018.
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMapObjectsRepository(mapApi: MapApi
    ): Repository<@JvmWildcard MapSearchResponse, @JvmWildcard MapSpecification> {
        return MapObjectsRepository(mapApi)
    }

    @Provides
    @Singleton
    fun provideMapFiltersRepository(mapApi: MapApi
    ):Repository<@JvmWildcard Map<String, Map<String, String>>, @JvmWildcard MapFiltersSpecification> {
        return MapFiltersRepository(mapApi)
    }
}