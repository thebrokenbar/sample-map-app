package pl.brokenpipe.vozillatest.view.filters.model

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
data class FilterItem(val label: String, val id: String? = null) {
    override fun toString(): String {
        return label
    }
}