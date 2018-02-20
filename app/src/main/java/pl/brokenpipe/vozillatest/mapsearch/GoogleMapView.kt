package pl.brokenpipe.vozillatest.mapsearch

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import pl.brokenpipe.vozillatest.MapsActivity
import pl.brokenpipe.vozillatest.R
import pl.brokenpipe.vozillatest.di.module.MapViewModule
import pl.brokenpipe.vozillatest.mapsearch.arch.MapView
import pl.brokenpipe.vozillatest.mapsearch.model.*
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
class GoogleMapView(mapsActivity: MapsActivity) : MapView, GoogleMap.OnCameraIdleListener {

    private val activityRef = WeakReference(mapsActivity)

    @Inject
    lateinit var iconGenerator: IconGenerator

    @Inject
    lateinit var vehicleClusterManager: ClusterManager<VehicleMarker>

    @Inject
    lateinit var parkingClusterManager: ClusterManager<ParkingMarker>

    @Inject
    lateinit var vehicleClusterRenderer: DefaultClusterRenderer<VehicleMarker>

    @Inject
    lateinit var parkingClusterRenderer: DefaultClusterRenderer<ParkingMarker>

    private lateinit var googleMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        activityRef.get()?.activityComponent
                ?.plus(MapViewModule(activityRef, this.googleMap))
                ?.inject(this)

        vehicleClusterManager.renderer = vehicleClusterRenderer
        parkingClusterManager.renderer = parkingClusterRenderer

        this.googleMap.setOnCameraIdleListener(this)

        testItems().forEach{
            addMarker(it)
        }
        vehicleClusterManager.cluster()
        parkingClusterManager.cluster()
    }

    override fun onCameraIdle() {
        vehicleClusterManager.onCameraIdle()
        parkingClusterManager.onCameraIdle()
    }

    override fun addZone(zone: Zone) {
        val polygonOptions = PolygonOptions()
                .addAll(zone.polygon)
                .fillColor(zone.fillColor)
                .strokeColor(zone.stokeColor)

        googleMap.addPolygon(polygonOptions)
    }

    override fun <T : Marker> addMarker(marker: T) {
        when (marker) {
            is VehicleMarker -> vehicleClusterManager.addItem(marker)
            is ParkingMarker -> parkingClusterManager.addItem(marker)
        }
    }


    //TODO
    private fun testItems(): List<Marker> {
        return listOf(
                VehicleMarker(LatLng(50.850236, 16.486074), "Świdnica Łukasińskiego", "", MapColor.GreenColor(), ""),
                VehicleMarker(LatLng(50.846533, 16.484635), "Świdnica Księcia Bolka", "", MapColor.GreenColor(), ""),
                ParkingMarker(LatLng(50.849277, 16.486490), "Świdnica Gdyńska", "", MapColor.BlueColor(),2)
        )
    }

}
