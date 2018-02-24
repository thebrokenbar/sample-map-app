package pl.brokenpipe.vozillatest.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.subjects.MaybeSubject
import io.reactivex.subjects.PublishSubject
import pl.brokenpipe.vozillatest.R
import pl.brokenpipe.vozillatest.platform.VozillaApplication
import pl.brokenpipe.vozillatest.di.component.ActivityComponent
import pl.brokenpipe.vozillatest.di.module.MapsActivityModule
import pl.brokenpipe.vozillatest.arch.mapsearch.MapView
import pl.brokenpipe.vozillatest.view.filters.FiltersDialogObservable
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), FiltersDialogObservable {

    @Inject
    lateinit var mapView: MapView

    lateinit var activityComponent: ActivityComponent
        private set

    private var filterDialogMaybeSubject = PublishSubject.create<SearchFilter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        activityComponent = (application as VozillaApplication).appComponent
                .plus(MapsActivityModule(this))

        activityComponent.inject(this)


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(mapView)
    }

    override fun getFilterDialogSubject(): PublishSubject<SearchFilter> {
        if(filterDialogMaybeSubject.hasComplete()) {
            filterDialogMaybeSubject = PublishSubject.create<SearchFilter>()
        }
        return filterDialogMaybeSubject
    }
}
