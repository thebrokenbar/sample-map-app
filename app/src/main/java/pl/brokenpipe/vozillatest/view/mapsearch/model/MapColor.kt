package pl.brokenpipe.vozillatest.view.mapsearch.model

import pl.brokenpipe.vozillatest.R

/**
 * Created by gwierzchanowski on 20.02.2018.
 */
sealed class MapColor(val colorResId: Int) {
    class BlueColor: MapColor(R.color.mapBlue)
    class RedColor: MapColor(R.color.mapRed)
    class GreenColor: MapColor(R.color.mapGreen)
    class BrownColor: MapColor(R.color.mapBrown)
    class YellowColor: MapColor(R.color.mapYellow)
    class OrangeColor: MapColor(R.color.mapOrange)
    class WhiteColor: MapColor(R.color.mapWhite)

    override fun equals(other: Any?): Boolean {
        return other != null && this::class == other::class
    }

}