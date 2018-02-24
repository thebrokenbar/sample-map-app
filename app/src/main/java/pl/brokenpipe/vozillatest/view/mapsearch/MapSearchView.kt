package pl.brokenpipe.vozillatest.view.mapsearch

import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolygonOptions
import io.reactivex.Single
import io.reactivex.SingleObserver
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
import pl.brokenpipe.vozillatest.view.mapsearch.cluster.ClusterOrchestrator
import pl.brokenpipe.vozillatest.view.mapsearch.model.Marker
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkersGroup
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
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

    private val activityRef = WeakReference(mapsActivity)

    private val mapCenterBoundariesPadding = mapsActivity.resources.getDimension(R.dimen.mapCenterBoundariesPadding)

    private val activity
        get() = activityRef.get() ?: throw IllegalStateException("Context is lost")

    private lateinit var googleMap: GoogleMap

    private var filtersDialogResultDisposable: Disposable? = null

    private val loadingObserver = object : SingleObserver<Any> {
        override fun onSuccess(t: Any) {
            releaseLoadingState()
        }

        override fun onSubscribe(d: Disposable) {
            clearAll()
            startLoadingState()
        }

        override fun onError(e: Throwable) {
            Timber.e(e)
            Toast.makeText(activityRef.get(), "Something went wrong :(", Toast.LENGTH_SHORT).show()
            releaseLoadingState()
        }
    }

    init {
        activity.filterButton.setOnClickListener { showFiltersDialog() }
        activity.refreshButton.setOnClickListener { refresh() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        activity.activityComponent
                .plus(MapViewModule(this.googleMap))
                .inject(this)

        this.googleMap.setOnCameraIdleListener(clusterOrchestrator)

        refresh()
    }

    private fun refresh() {
        presenter.getSearchFilter()
                .flatMap { fetchByFilter(it) }
                .subscribe(loadingObserver)
    }

    private fun addAllMarkers(markersGroupMap: Map<MarkersGroup, List<Marker>>) {
        markersGroupMap.forEach { cluster ->
            cluster.value.forEach {
                addMarker(cluster.key, it)
            }
        }
        clusterOrchestrator.clusterAll()
    }

    private fun fetchByFilter(searchFilter: SearchFilter): Single<Map<MarkersGroup, List<Marker>>> {
        return presenter.getMarkersGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { clusterOrchestrator.initialize(it) }
                .observeOn(Schedulers.io())
                .flatMap { presenter.fetchMapObjects(searchFilter) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    addAllMarkers(it)
                    centerMapOnAllMarkers(it)
                }

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
        activityRef.get()?.mapLoadingProgressBar?.visibility = View.VISIBLE
    }

    private fun addZone(zone: Zone) {
        val polygonOptions = PolygonOptions()
                .addAll(zone.polygon)
                .fillColor(zone.fillColor)
                .strokeColor(zone.stokeColor)

        googleMap.addPolygon(polygonOptions)
    }

    private fun addMarker(clusterType: MarkersGroup, marker: Marker) {
        clusterOrchestrator.add(clusterType, marker)
    }

    private fun showFiltersDialog() {
        if(filtersDialogResultDisposable?.isDisposed == false) {
            filtersDialogResultDisposable?.dispose()
        }

        this.filtersDialogResultDisposable = activity.getFilterDialogSubject()
                .doOnSubscribe { FiltersDialog().show(activity.supportFragmentManager, TAG) }
                .subscribeBy(
                        onNext = { fetchByFilter(it).subscribe(loadingObserver) },
                        onError = { loadingObserver.onError(it) }
                )
    }

    private fun clearAll() {
        clusterOrchestrator.clearAllMarkers()
        googleMap.clear()
    }

}
