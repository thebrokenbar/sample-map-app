package pl.brokenpipe.vozillatest.di.module

import dagger.Module
import dagger.Provides
import pl.brokenpipe.vozillatest.arch.Arch
import pl.brokenpipe.vozillatest.interactor.GetClusterTypes
import pl.brokenpipe.vozillatest.interactor.GetMapObjects
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.interactor.model.MapObject
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetClusterTypesUseCase() : Arch.UseCase<@JvmWildcard Unit, @JvmWildcard List<ClusterType>> {
        return GetClusterTypes()
    }

    @Provides
    @Singleton
    fun provideGetMapObjectsUseCase(): Arch.UseCase<@JvmWildcard List<String>, @JvmWildcard Map<ClusterType, List<MapObject>>> {
        return GetMapObjects()
    }
}