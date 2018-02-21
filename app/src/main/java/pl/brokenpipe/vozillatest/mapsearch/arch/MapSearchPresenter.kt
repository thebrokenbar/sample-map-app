package pl.brokenpipe.vozillatest.mapsearch.arch

import io.reactivex.Single
import pl.brokenpipe.vozillatest.arch.Arch
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.mapsearch.cluster.Cluster
import pl.brokenpipe.vozillatest.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.mapsearch.model.SearchFilter


/**
 * Created by gwierzchanowski on 20.02.2018.
 */
interface MapSearchPresenter : Arch.Presenter {
    fun getClusterConfigs(): Single<List<Cluster>>
    fun getMarkers(searchFilter: SearchFilter): Single<Map<Cluster, List<Marker>>>
}