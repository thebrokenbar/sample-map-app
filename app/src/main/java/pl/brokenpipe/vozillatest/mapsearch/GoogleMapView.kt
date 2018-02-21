package pl.brokenpipe.vozillatest.mapsearch

import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolygonOptions
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_maps.*
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.di.module.MapViewModule
import pl.brokenpipe.vozillatest.interactor.model.ClusterType
import pl.brokenpipe.vozillatest.mapsearch.arch.MapSearchPresenter
import pl.brokenpipe.vozillatest.mapsearch.arch.MapView
import pl.brokenpipe.vozillatest.mapsearch.cluster.Cluster
import pl.brokenpipe.vozillatest.mapsearch.cluster.ClusterOrchestrator
import pl.brokenpipe.vozillatest.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.mapsearch.model.SearchFilter
import pl.brokenpipe.vozillatest.mapsearch.model.Zone
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class GoogleMapView(mapsActivity: MapsActivity,
                    private val presenter: MapSearchPresenter) : MapView {

    private val activityRef = WeakReference(mapsActivity)

    @Inject
    lateinit var clusterOrchestrator: ClusterOrchestrator

    private lateinit var googleMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val activity = activityRef.get() ?: throw IllegalStateException("Context is lost")

        activity.activityComponent
                .plus(MapViewModule(activityRef, this.googleMap))
                .inject(this)

        configureClusters()

        this.googleMap.setOnCameraIdleListener(clusterOrchestrator)
    }

    fun refresh() {
        presenter.getMarkers(getSearchFilter())
                .doOnSuccess { addAllMarkers(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loadingObserver())
    }


    private fun addAllMarkers(clusterMap: Map<Cluster, List<Marker>>) {
        clusterMap.forEach { cluster ->
            cluster.value.forEach {
                addMarker(cluster.key, it)
            }
        }
    }

    private fun configureClusters() {
        presenter.getClusterConfigs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { clusterOrchestrator.initialize(it) }
                .observeOn(Schedulers.io())
                .flatMap { presenter.getMarkers(getSearchFilter()) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { addAllMarkers(it) }
                .subscribe(loadingObserver())
    }

    private fun releaseLoadingState() {
        activityRef.get()?.mapLoadingProgressBar?.visibility = View.GONE
    }

    private fun startLoadingState() {
        activityRef.get()?.mapLoadingProgressBar?.visibility = View.VISIBLE
    }

    private fun addZone(zone: Zone) {
        val polygonOptions = PolygonOptions()
                .addAll(zone.polygon)
                .fillColor(zone.fillColor)
                .strokeColor(zone.stokeColor)

        googleMap.addPolygon(polygonOptions)
    }

    private fun addMarker(clusterType: Cluster, marker: Marker) {
        clusterOrchestrator.add(clusterType, marker)
    }

    private fun loadingObserver() = object : SingleObserver<Any> {
        override fun onSuccess(t: Any) {
            releaseLoadingState()
        }

        override fun onSubscribe(d: Disposable) {
            startLoadingState()
        }

        override fun onError(e: Throwable) {
            Timber.e(e)
            Toast.makeText(activityRef.get(), "Something went wrong :(", Toast.LENGTH_SHORT).show()
        }

    }

    //MOCK TODO
    private fun getSearchFilter() = SearchFilter(listOf("VEHICLE", "PARKING"))

}