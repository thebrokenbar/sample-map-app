package pl.brokenpipe.vozillatest.view.mapsearch.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by gwierzchanowski on 21.02.2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SearchFilter(
        val objectTypes: List<String>,
        val vehicleStatus: String? = null,
        val vehicleModel: String? = null
) : Parcelable