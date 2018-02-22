package pl.brokenpipe.vozillatest.presenter.mapsearch

import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.Single
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.interactor.model.MapObject
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkersGroup
import pl.brokenpipe.vozillatest.view.mapsearch.model.MapColor
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class MapSearchViewModel(
        private val getClusterTypes: UseCase<Unit, List<ClusterType>>,
        private val getMapObjects: UseCase<List<String>, Map<ClusterType, List<MapObject>>>
) : ViewModel(), MapSearchPresenter {
    companion object {

        val CLUSTER_TYPES_COLORS = hashMapOf(
                Pair("VEHICLE", MapColor.GreenColor()),
                Pair("CHARGER", MapColor.YellowColor()),
                Pair("POI", MapColor.OrangeColor()),
                Pair("PARKING", MapColor.BlueColor())
        )
        val DEFAULT_CLUSTER_COLOR = MapColor.WhiteColor()
    }

    override fun getMarkersGroup(): Single<List<MarkersGroup>> {
        return getClusterTypes.execute()
                .flatMap { Observable.fromIterable(it) }
                .map { parseClusterTypeToMarkersGroup(it) }
                .toList()
    }

    private fun parseClusterTypeToMarkersGroup(clusterType: ClusterType): MarkersGroup {
        val color = getMapColor(clusterType.id)
        return MarkersGroup(clusterType.id, color, clusterType.options)
    }

    private fun getMapColor(clusterTypeId: String) =
            CLUSTER_TYPES_COLORS.getOrElse(clusterTypeId) { DEFAULT_CLUSTER_COLOR }

    override fun getMarkers(searchFilter: SearchFilter): Single<Map<MarkersGroup, List<Marker>>> {
        return getMapObjects.execute(searchFilter.objectTypes)
                .first(emptyMap())
                .flatMap { Single.just(prepareClusters(it)) }
    }

    private fun prepareClusters(map: Map<ClusterType, List<MapObject>>): HashMap<MarkersGroup, List<Marker>> {
        val clusters = hashMapOf<MarkersGroup, List<Marker>>()

        map.forEach { mapEntry ->
            val cluster = parseClusterTypeToMarkersGroup(mapEntry.key)
            val markers: List<Marker> = mapEntry.value
                    .map {
                        Marker(it.id, LatLng(it.position.first, it.position.second),
                                it.name, it.description, getMapColor(cluster.id))
                    }
            clusters[cluster] = markers
        }
        return clusters
    }


}