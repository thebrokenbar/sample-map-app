package pl.brokenpipe.vozillatest.view

import android.content.Context
import android.os.Build
import pl.brokenpipe.vozillatest.view.mapsearch.model.MapColor

/**
 * Created by gwierzchanowski on 24.02.2018.
 */

fun MapColor.intColor(context: Context?): Int {
    return context?.let {
        return@let if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(this.colorResId)
        } else {
            @Suppress("DEPRECATION")
            context.resources?.getColor(this.colorResId)
        }
    } ?: throw IllegalStateException("Context is lost")
}