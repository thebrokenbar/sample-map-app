package pl.brokenpipe.vozillatest.presenter.mapsearch

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.brokenpipe.vozillatest.arch.UseCase
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.interactor.model.MapObject

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
@RunWith(MockitoJUnitRunner::class)
class MapSearchViewModelTest {

    private lateinit var mapSearchViewModel: MapSearchViewModel

    @Mock
    lateinit var getClusterTypes: UseCase<Unit, List<ClusterType>>

    @Mock
    lateinit var getMapObjects: UseCase<List<String>, Map<ClusterType, List<MapObject>>>

    @Before
    fun setUp() {
        mapSearchViewModel = MapSearchViewModel(getClusterTypes, getMapObjects)
    }

    @Test
    fun `return adequate markers groups when queried for cluster types`() {
        //given
        val clusterTypes = listOf(ClusterType("clusterId", "any Label", "not null options"))
        whenever(getClusterTypes.execute()).thenReturn(Observable.just(clusterTypes))

        //when
        val test = mapSearchViewModel.getMarkersGroup().test()

        //then

        test.assertComplete()
        test.assertValue {
            it.size == clusterTypes.size && it[0].id == clusterTypes[0].id && it[0].options == clusterTypes[0].options
        }
    }

}