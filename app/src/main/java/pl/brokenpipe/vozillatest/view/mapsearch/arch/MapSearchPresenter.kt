package pl.brokenpipe.vozillatest.view.mapsearch.arch

import io.reactivex.Single
import pl.brokenpipe.vozillatest.arch.Arch
import pl.brokenpipe.vozillatest.view.mapsearch.cluster.MarkersGroup
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter


/**
 * Created by gwierzchanowski on 20.02.2018.
 */
interface MapSearchPresenter : Arch.Presenter {
    fun getMarkersGroup(): Single<List<MarkersGroup>>
    fun getMarkers(searchFilter: SearchFilter): Single<Map<MarkersGroup, List<Marker>>>
}