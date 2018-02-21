package pl.brokenpipe.vozillatest.mapsearch

import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.Single
import pl.brokenpipe.vozillatest.arch.Arch
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.interactor.model.MapObject
import pl.brokenpipe.vozillatest.mapsearch.arch.MapSearchPresenter
import pl.brokenpipe.vozillatest.mapsearch.cluster.Cluster
import pl.brokenpipe.vozillatest.mapsearch.model.MapColor
import pl.brokenpipe.vozillatest.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.mapsearch.model.SearchFilter

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class MapSearchViewModel(
        private val getClusterTypes: Arch.UseCase<Unit, List<ClusterType>>,
        private val getMapObjects: Arch.UseCase<List<String>, Map<ClusterType, List<MapObject>>>
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

    override fun getClusterConfigs(): Single<List<Cluster>> {
        return getClusterTypes.execute()
                .flatMap { Observable.fromIterable(it) }
                .map { parseClusterTypeToCluster(it) }
                .toList()
    }

    private fun parseClusterTypeToCluster(clusterType: ClusterType): Cluster {
        val color = getMapColor(clusterType.id)
        return Cluster(clusterType.id, color, clusterType.options)
    }

    private fun getMapColor(clusterTypeId: String) =
            CLUSTER_TYPES_COLORS.getOrElse(clusterTypeId) { DEFAULT_CLUSTER_COLOR }

    override fun getMarkers(searchFilter: SearchFilter): Single<Map<Cluster, List<Marker>>> {
        return getMapObjects.execute(searchFilter.objectTypes)
                .first(emptyMap())
                .flatMap { Single.just(prepareClusters(it)) }
    }

    private fun prepareClusters(map: Map<ClusterType, List<MapObject>>): HashMap<Cluster, List<Marker>> {
        val clusters = hashMapOf<Cluster, List<Marker>>()

        map.forEach { mapEntry ->
            val cluster = parseClusterTypeToCluster(mapEntry.key)
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