package pl.brokenpipe.vozillatest.di.component

import dagger.Component
import pl.brokenpipe.vozillatest.VozillaApplication
import pl.brokenpipe.vozillatest.di.module.MapsActivityModule
import pl.brokenpipe.vozillatest.di.module.AppModule
import pl.brokenpipe.vozillatest.di.module.UseCaseModule
import javax.inject.Singleton

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Singleton
@Component(modules = [AppModule::class, UseCaseModule::class])
interface AppComponent {
    fun inject(app: VozillaApplication)
    fun plus(mapsActivityModule: MapsActivityModule): ActivityComponent
}