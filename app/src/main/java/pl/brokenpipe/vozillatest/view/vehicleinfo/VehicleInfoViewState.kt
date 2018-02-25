package pl.brokenpipe.vozillatest.view.vehicleinfo

import android.databinding.BaseObservable
import android.databinding.Bindable
import pl.brokenpipe.vozillatest.view.databinding.bindable

/**
 * Created by gwierzchanowski on 25.02.2018.
 */
class VehicleInfoViewState: BaseObservable() {
    @get:Bindable var brand: String by bindable("")

    @get:Bindable var range: String by bindable("")

    @get:Bindable var registerPlates: String by bindable("")

    @get:Bindable var reservation: String? by bindable(null)

    @get:Bindable var pictureUrl: String by bindable("")


}