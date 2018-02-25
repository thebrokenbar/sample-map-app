package pl.brokenpipe.vozillatest.view.vehicleinfo

import android.app.Dialog
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.View
import pl.brokenpipe.vozillatest.databinding.VehicleInfoBinding
import pl.brokenpipe.vozillatest.view.MapsActivity
import pl.brokenpipe.vozillatest.view.mapsearch.model.MarkerInfo


/**
 * Created by gwierzchanowski on 25.02.2018.
 */
class VehicleInfoBottomsheet : BottomSheetDialogFragment() {
    companion object {
        const val VEHICLE_INFO_ARG = "VEHICLE_INFO_ARG"
    }

    private lateinit var mapsActivity: MapsActivity

    private val bottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }

        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        mapsActivity = context as MapsActivity? ?: throw IllegalStateException("There is no context here...")
        mapsActivity.activityComponent.inject(this)

        val inflater = mapsActivity.layoutInflater
        val bindings = VehicleInfoBinding.inflate(inflater)

        val viewState = createViewState(arguments?.get(VEHICLE_INFO_ARG) as MarkerInfo.VehicleInfo?)
        bindings.viewState = viewState
        dialog.setContentView(bindings.root)

        val params = (bindings.root.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior) {
            behavior.setBottomSheetCallback(bottomSheetBehaviorCallback)
        }
    }

    private fun createViewState(vehicleMarkerInfo: MarkerInfo.VehicleInfo?): VehicleInfoViewState {
        return vehicleMarkerInfo?.let {
            VehicleInfoViewState().apply {
                brand = vehicleMarkerInfo.name
                range = "${vehicleMarkerInfo.rangeKm}km (${vehicleMarkerInfo.batteryLevelPct}%)"
                registerPlates = vehicleMarkerInfo.platesNumber
                reservation = vehicleMarkerInfo.reservationEnd
                pictureUrl = vehicleMarkerInfo.picture
            }
        } ?: throw IllegalArgumentException("Empty VehicleInfo")
    }
}