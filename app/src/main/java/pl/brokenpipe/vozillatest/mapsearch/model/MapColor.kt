package pl.brokenpipe.vozillatest.mapsearch.model

import pl.brokenpipe.vozillatest.R

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
sealed class MapColor(val colorResId: Int) {
    class BlueColor: MapColor(R.color.markerBlue)
    class RedColor: MapColor(R.color.markerRed)
    class GreenColor: MapColor(R.color.markerGreen)
    class BrownColor: MapColor(R.color.markerBrown)

}