package pl.brokenpipe.vozillatest.view.filters

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.MaybeSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.filters.view.*
import pl.brokenpipe.vozillatest.R
import pl.brokenpipe.vozillatest.arch.mapsearch.MapSearchPresenter
import pl.brokenpipe.vozillatest.constant.ResourceTypes
import pl.brokenpipe.vozillatest.view.MapsActivity
import pl.brokenpipe.vozillatest.view.filters.model.FilterItem
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by wierzchanowskig on 23.02.2018.
 */
class FiltersDialog : DialogFragment() {

    @Inject
    protected lateinit var presenter: MapSearchPresenter

    private lateinit var rootView: View

    private lateinit var mapsActivity: MapsActivity

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mapsActivity = context as MapsActivity? ?: throw IllegalStateException("There is no context here...")
        mapsActivity.activityComponent.inject(this)

        val inflater = mapsActivity.layoutInflater
        rootView = inflater.inflate(R.layout.filters, null)


        attachAdapters()
                .flatMap { loadFilters() }
                .subscribeBy(onError = { showError(it) })

        return AlertDialog.Builder(mapsActivity)
                .setView(rootView)
                .setPositiveButton(R.string.filter) { dialogInterface, i ->
                    positiveButtonAction(dialogInterface, i)
                }
                .setNegativeButton(R.string.cancel) { dialogInterface, i ->
                    negativeButtonAction(dialogInterface, i)
                }
                .create()
    }

    private fun loadFilters() = presenter.getSearchFilter()
            .firstOrError()
            .doOnSuccess { setFilters(it) }

    private fun attachAdapters(): Single<SubFilterAdapter> {
        return presenter.fetchVehicleModelsToFilter()
                .map { addAllFilterOption(it) }
                .map { SubFilterAdapter(mapsActivity, it) }
                .doOnSuccess { rootView.filterVehicleModelSpinner.adapter = it }
                .flatMap { presenter.fetchVehicleStatusesToFilter() }
                .map { addAllFilterOption(it) }
                .map { SubFilterAdapter(mapsActivity, it) }
                .doOnSuccess { rootView.filterVehicleStatusSpinner.adapter = it }
    }

    private fun addAllFilterOption(items: List<FilterItem>) =
            mutableListOf(FilterItem(mapsActivity.getString(R.string.anyFilter)))
                    .also { it.addAll(items) }

    private fun showError(error: Throwable) {
        Timber.e(error)
        Toast.makeText(mapsActivity, "Cannot load filters", Toast.LENGTH_SHORT).show()
    }

    private fun setFilters(searchFilter: SearchFilter) {
        if (searchFilter.objectTypes.contains(ResourceTypes.VEHICLE)) {
            rootView.filterVehicleCheckbox.isChecked = true
        }
        if (searchFilter.objectTypes.contains(ResourceTypes.PARKING)) {
            rootView.filterParkingCheckbox.isChecked = true
        }
        if (searchFilter.objectTypes.contains(ResourceTypes.CHARGER)) {
            rootView.filterChargerCheckbox.isChecked = true
        }
        if (searchFilter.objectTypes.contains(ResourceTypes.POI)) {
            rootView.filterPoiCheckbox.isChecked = true
        }
        if (searchFilter.objectTypes.contains(ResourceTypes.ZONE)) {
            rootView.filterZoneCheckbox.isChecked = true
        }

        val vehicleFilterModelAdapter = rootView.filterVehicleModelSpinner.adapter as SubFilterAdapter
        rootView.filterVehicleModelSpinner.setSelection(
                findSubFilterPosition(vehicleFilterModelAdapter.data, searchFilter.vehicleModel))

        val vehicleFilterStatusAdapter = rootView.filterVehicleStatusSpinner.adapter as SubFilterAdapter
        rootView.filterVehicleStatusSpinner.setSelection(
                findSubFilterPosition(vehicleFilterStatusAdapter.data, searchFilter.vehicleStatus))

    }

    private fun findSubFilterPosition(adapterData: List<FilterItem>, searchFilterSubFilter: String?): Int {
        var itemPosition = 0
        if (searchFilterSubFilter != null) {
            adapterData.forEachIndexed { index, filterItem ->
                if (filterItem.id == searchFilterSubFilter) {
                    itemPosition = index
                }
            }
        }
        return itemPosition
    }

    private fun negativeButtonAction(dialogInterface: DialogInterface, i: Int) {
        dialogInterface.cancel()
    }

    private fun positiveButtonAction(dialogInterface: DialogInterface, i: Int) {
        val objectTypes = mutableListOf<String>()
        if (rootView.filterVehicleCheckbox.isChecked) objectTypes.add(ResourceTypes.VEHICLE)
        if (rootView.filterParkingCheckbox.isChecked) objectTypes.add(ResourceTypes.PARKING)
        if (rootView.filterChargerCheckbox.isChecked) objectTypes.add(ResourceTypes.CHARGER)
        if (rootView.filterPoiCheckbox.isChecked) objectTypes.add(ResourceTypes.POI)
        if (rootView.filterZoneCheckbox.isChecked) objectTypes.add(ResourceTypes.ZONE)

        val selectedModel = rootView.filterVehicleModelSpinner.selectedItem as FilterItem?
        val selectedStatus = rootView.filterVehicleStatusSpinner.selectedItem as FilterItem?

        val searchFilter = SearchFilter(objectTypes, selectedStatus?.id, selectedModel?.id)

        presenter.setSearchFilter(searchFilter).subscribeBy(onError = { showError(it) })

        dialogInterface.dismiss()
    }
}