package pl.brokenpipe.vozillatest.di.component

import dagger.Subcomponent
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.di.module.MapViewModule
import pl.brokenpipe.vozillatest.di.module.MapsActivityModule

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@Subcomponent(modules = [MapsActivityModule::class])
interface ActivityComponent {
    fun inject(mapsActivity: MapsActivity)
    fun plus(mapViewModule: MapViewModule): MapViewComponent
}