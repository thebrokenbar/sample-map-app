package pl.brokenpipe.vozillatest.arch

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory<out VM : ViewModel>(
        private val creator: () -> VM)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}