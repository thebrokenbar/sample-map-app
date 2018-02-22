package pl.brokenpipe.vozillatest.interactor

import io.reactivex.Observable
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.interactor.model.ClusterType

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
class GetClusterTypes: UseCase<Unit, List<ClusterType>> {
    override fun execute(param: Unit?): Observable<List<ClusterType>> {
        //TODO
        //mock
        return Observable.just(listOf(
                ClusterType("VEHICLE", "Pojazdy NISSAN LEAF", "LEAF"),
                ClusterType("VEHICLE", "Pojazdy NISSAN e-NV200", "e-NV200"),
                ClusterType("CHARGER", "Ładowarki"),
                ClusterType("POI", "Punkty szczególne"),
                ClusterType("PARKING", "Parkingi")
        ))
    }

}