package pl.brokenpipe.vozillatest.di.component

import dagger.Component
import pl.brokenpipe.vozillatest.di.module.*
import pl.brokenpipe.vozillatest.platform.VozillaApplication
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Singleton
@Component(modules = [AppModule::class, UseCaseModule::class, PresenterModule::class,
    RepositoryModule::class, CacheModule::class, ApiModule::class])
interface AppComponent {
    fun inject(app: VozillaApplication)
    fun plus(mapsActivityModule: MapsActivityModule): ActivityComponent
}