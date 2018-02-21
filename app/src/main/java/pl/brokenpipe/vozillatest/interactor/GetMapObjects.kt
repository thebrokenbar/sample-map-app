package pl.brokenpipe.vozillatest.interactor

import io.reactivex.Observable
import pl.brokenpipe.vozillatest.arch.Arch
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.interactor.model.MapObject

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
class GetMapObjects : Arch.UseCase<List<String>, Map<ClusterType, List<MapObject>>> {
    override fun execute(param: List<String>?): Observable<Map<ClusterType, List<MapObject>>> {
        //TODO
        //mock
        return Observable.just(
                hashMapOf(
                        Pair(ClusterType("VEHICLE", "", "LEAF"), listOf(
                                MapObject("VEHICLE1", Pair(50.850236, 16.486074), "Nissan Leaf 1", "Dostępny"),
                                MapObject("VEHICLE2", Pair(50.846533, 16.484635), "Nissan Leaf 2", "Zajęty"),
                                MapObject("VEHICLE3", Pair(50.849277, 16.486490), "Nissan Leaf 3", "Zarezerwowany")
                        )),
                        Pair(ClusterType("VEHICLE", "", "e-NV200"), listOf(
                                MapObject("VEHICLE4", Pair(50.849623, 16.488097), "NISSAN e-NV200 1", "Dostępny"),
                                MapObject("VEHICLE5", Pair(50.850231, 16.485511), "NISSAN e-NV200 2", "Zajęty"),
                                MapObject("VEHICLE6", Pair(50.850136, 16.488175), "NISSAN e-NV200 3", "Zarezerwowany")
                        )),
                        Pair(ClusterType("PARKING", ""), listOf(
                                MapObject("PARKING1", Pair(50.848787, 16.483775), "2/4", "Parking Kanonierska"),
                                MapObject("PARKING2", Pair(50.847703, 16.483786), "1/10", "Parking Policja"),
                                MapObject("PARKING3", Pair(50.847073, 16.482648), "2/3", "Parking Boczna")
                        ))
                )
        )
    }
}