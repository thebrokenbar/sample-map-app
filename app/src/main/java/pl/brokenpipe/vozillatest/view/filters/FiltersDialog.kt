package pl.brokenpipe.vozillatest.view.filters

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import io.reactivex.subjects.MaybeSubject
import kotlinx.android.synthetic.main.filters.view.*
import pl.brokenpipe.vozillatest.R
import pl.brokenpipe.vozillatest.constant.ResourceTypes
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
import timber.log.Timber

/**
 * Created by wierzchanowskig on 23.02.2018.
 */
class FiltersDialog : DialogFragment() {

    companion object {
        const val SEARCH_FILTER_ARG = "SEARCH_FILTER_ARG"
    }

    private lateinit var dialogSubject: MaybeSubject<SearchFilter>

    private lateinit var rootView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            val dialogObservable = context as FiltersDialogObservable
            this.dialogSubject = dialogObservable.getFilterDialogSubject()
        } catch (e: ClassCastException) {
            Toast.makeText(context, "Error, check logs", Toast.LENGTH_SHORT).show()
            this.dismiss()
            Timber.e(e)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity ?: throw IllegalStateException("There is no context here...")
        val inflater = context.layoutInflater
        rootView = inflater.inflate(R.layout.filters, null)

        val searchFilter = arguments?.get(SEARCH_FILTER_ARG) as SearchFilter?
        searchFilter?.let { loadSavedFilters(it) }

//        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line,
//                listOf("Wszystkie" , "Model1", "Model2"))
//        rootView.filterVehicleModelSpinner.adapter = adapter

        return AlertDialog.Builder(context)
                .setView(rootView)
                .setPositiveButton(R.string.filter) { dialogInterface, i ->
                    positiveButtonAction(dialogInterface, i)
                }
                .setNegativeButton(R.string.cancel) { dialogInterface, i ->
                    negativeButtonAction(dialogInterface, i)
                }
                .create()
    }

    private fun loadSavedFilters(searchFilter: SearchFilter) {
        if(searchFilter.objectTypes.contains(ResourceTypes.VEHICLE)) {
            rootView.filterVehicleCheckbox.isChecked = true
        }
        if(searchFilter.objectTypes.contains(ResourceTypes.PARKING)) {
            rootView.filterParkingCheckbox.isChecked = true
        }
        if(searchFilter.objectTypes.contains(ResourceTypes.CHARGER)) {
            rootView.filterChargerCheckbox.isChecked = true
        }
        if(searchFilter.objectTypes.contains(ResourceTypes.POI)) {
            rootView.filterPoiCheckbox.isChecked = true
        }
        if(searchFilter.objectTypes.contains(ResourceTypes.ZONE)) {
            rootView.filterZoneCheckbox.isChecked = true
        }
    }

    private fun negativeButtonAction(dialogInterface: DialogInterface, i: Int) {
        dialogSubject.onComplete()
        dialogInterface.cancel()
    }

    private fun positiveButtonAction(dialogInterface: DialogInterface, i: Int) {
        val objectTypes = mutableListOf<String>()
        if (rootView.filterVehicleCheckbox.isChecked) objectTypes.add(ResourceTypes.VEHICLE)
        if (rootView.filterParkingCheckbox.isChecked) objectTypes.add(ResourceTypes.PARKING)
        if (rootView.filterChargerCheckbox.isChecked) objectTypes.add(ResourceTypes.CHARGER)
        if (rootView.filterPoiCheckbox.isChecked) objectTypes.add(ResourceTypes.POI)
        if (rootView.filterZoneCheckbox.isChecked) objectTypes.add(ResourceTypes.ZONE)

        val searchFilter = SearchFilter(objectTypes)

        dialogSubject.onSuccess(searchFilter)
        dialogInterface.dismiss()
    }
}