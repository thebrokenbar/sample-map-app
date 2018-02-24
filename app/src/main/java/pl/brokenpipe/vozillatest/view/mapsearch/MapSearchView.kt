package pl.brokenpipe.vozillatest.view.mapsearch

import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_maps.*
import pl.brokenpipe.vozillatest.R
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.arch.mapsearch.MapView
import pl.brokenpipe.vozillatest.di.module.MapViewModule
import pl.brokenpipe.vozillatest.view.MapsActivity
import pl.brokenpipe.vozillatest.view.filters.FiltersDialog
import pl.brokenpipe.vozillatest.view.mapsearch.mapelements.ClusterOrchestrator
import pl.brokenpipe.vozillatest.view.mapsearch.mapelements.ZoneOrchestrator
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkersGroup
import pl.brokenpipe.vozillatest.view.mapsearch.model.Zone
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class MapSearchView(mapsActivity: MapsActivity,
                    private val presenter: MapSearchPresenter) : MapView {

    companion object {
        const val TAG = "MapSearchView"
    }

    @Inject
    lateinit var clusterOrchestrator: ClusterOrchestrator

    @Inject
    lateinit var zoneOrchestrator: ZoneOrchestrator

    private val activityRef = WeakReference(mapsActivity)

    private val mapCenterBoundariesPadding = mapsActivity.resources.getDimension(R.dimen.mapCenterBoundariesPadding)

    private val activity
        get() = activityRef.get() ?: throw IllegalStateException("Context is lost")

    private lateinit var googleMap: GoogleMap

    private var filtersDialogResultDisposable: Disposable? = null

    private val loadingObserver = object : Observer<Unit> {
        override fun onComplete() {
            Timber.d("Search Observer completed")
        }

        override fun onNext(t: Unit) {
            releaseLoadingState()
        }

        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {
            showError(e)
            observeFilterChanges()
        }
    }

    private fun showError(e: Throwable) {
        Timber.e(e)
        Toast.makeText(activityRef.get(), "Something went wrong :(", Toast.LENGTH_SHORT).show()
        releaseLoadingState()
    }

    init {
        activity.filterButton.setOnClickListener { showFiltersDialog() }
        activity.refreshButton.setOnClickListener { manualRefresh() }
    }

    private fun manualRefresh() {
        presenter.manualRefresh()
                .subscribeBy(onError = { loadingObserver.onError(it) })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        activity.activityComponent
                .plus(MapViewModule(this.googleMap))
                .inject(this)

        this.googleMap.setOnCameraIdleListener(clusterOrchestrator)

        observeFilterChanges()
        manualRefresh()
    }

    private fun addAllMarkers(markersGroupMap: Map<MarkersGroup, List<Marker>>) {
        markersGroupMap.forEach { cluster ->
            cluster.value.forEach {
                addMarker(cluster.key, it)
            }
        }
        clusterOrchestrator.clusterAll()
    }

    private fun observeFilterChanges() {
        return presenter.getSearchFilter()
                .doOnNext { startLoadingState() }
                .flatMapSingle {
                    presenter.getMarkersGroup()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess { clusterOrchestrator.initialize(it) }
                            .observeOn(Schedulers.io())
                            .flatMap { presenter.fetchMarkers() }
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess {
                                addAllMarkers(it)
                                centerMapOnAllMarkers(it)
                            }
                            .observeOn(Schedulers.io())
                            .flatMap { presenter.getZones() }
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess { addAllZones(it) }
                            .map { Unit }
                }
                .subscribe(loadingObserver)
    }

    private fun addAllZones(zones: List<Zone>) {
        zones.forEach { zoneOrchestrator.add(it) }
    }

    private fun centerMapOnAllMarkers(markers: Map<MarkersGroup, List<Marker>>) {
        if (markers.any { it.value.isNotEmpty() }) {
            val allMarkers = mutableListOf<Marker>()
            markers.forEach { group ->
                group.value.forEach {
                    allMarkers.add(it)
                }
            }

            val boundsBuilder = LatLngBounds.builder()
            allMarkers.forEach {
                boundsBuilder.include(it.position)
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),
                    mapCenterBoundariesPadding.toInt()))
        }
    }

    private fun releaseLoadingState() {
        activityRef.get()?.mapLoadingProgressBar?.visibility = View.GONE
    }

    private fun startLoadingState() {
        clearAll()
        activityRef.get()?.mapLoadingProgressBar?.visibility = View.VISIBLE
    }

    private fun addMarker(clusterType: MarkersGroup, marker: Marker) {
        clusterOrchestrator.add(clusterType, marker)
    }

    private fun showFiltersDialog() {
        if (filtersDialogResultDisposable?.isDisposed == false) {
            filtersDialogResultDisposable?.dispose()
        }

        Completable.fromAction { FiltersDialog().show(activity.supportFragmentManager, TAG) }
                .subscribeBy(onError = { loadingObserver.onError(it) })
    }

    private fun clearAll() {
        clusterOrchestrator.clearAllMarkers()
        googleMap.clear()
    }

}
