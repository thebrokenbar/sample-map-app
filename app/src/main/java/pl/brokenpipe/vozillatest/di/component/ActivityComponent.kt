package pl.brokenpipe.vozillatest.di.component

import dagger.Subcomponent
import pl.brokenpipe.vozillatest.view.MapsActivity
import pl.brokenpipe.vozillatest.di.module.MapViewModule
import pl.brokenpipe.vozillatest.di.module.MapsActivityModule
import pl.brokenpipe.vozillatest.di.scope.ActivityScope

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Subcomponent(modules = [MapsActivityModule::class])
@ActivityScope
interface ActivityComponent {
    fun inject(mapsActivity: MapsActivity)
    fun plus(mapViewModule: MapViewModule): MapViewComponent
}