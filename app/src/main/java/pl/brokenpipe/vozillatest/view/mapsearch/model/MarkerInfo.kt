package pl.brokenpipe.vozillatest.view.mapsearch.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by gwierzchanowski on 22.02.2018.
 */
sealed class MarkerInfo {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class VehicleInfo(
            var rangeKm: String,
            var name: String,
            var platesNumber: String,
            var reservationEnd: String?,
            var batteryLevelPct: Int,
            var picture: String
    ): MarkerInfo(), Parcelable
}