package pl.brokenpipe.vozillatest.view.filters

import io.reactivex.subjects.MaybeSubject
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter

/**
 * Created by wierzchanowskig on 23.02.2018.
 */
interface FiltersDialogObservable {
    fun getFilterDialogSubject(): MaybeSubject<SearchFilter>
}