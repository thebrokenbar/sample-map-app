package pl.brokenpipe.vozillatest.di.component

import dagger.Subcomponent
import pl.brokenpipe.vozillatest.di.module.MapViewModule
import pl.brokenpipe.vozillatest.di.scope.ViewScope
import pl.brokenpipe.vozillatest.view.filters.FiltersDialog
import pl.brokenpipe.vozillatest.view.mapsearch.MapSearchView

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
@ViewScope
@Subcomponent(modules = [MapViewModule::class])
interface MapViewComponent {
    fun inject(mapSearchView: MapSearchView)
}