package pl.brokenpipe.vozillatest.view.filters

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Toast
import io.reactivex.subjects.MaybeSubject
import io.reactivex.subjects.PublishSubject
import pl.brokenpipe.vozillatest.R
import pl.brokenpipe.vozillatest.view.mapsearch.model.SearchFilter
import timber.log.Timber

/**
 * Created by wierzchanowskig on 23.02.2018.
 */
class FiltersDialog: DialogFragment(){

    private lateinit var dialogSubject: MaybeSubject<SearchFilter>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            val dialogObservable = context as FiltersDialogObservable
            this.dialogSubject = dialogObservable.getSubject()
        } catch (e: ClassCastException){
            Toast.makeText(context, "Error, check logs", Toast.LENGTH_SHORT).show()
            this.dismiss()
            Timber.e(e)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity ?: throw IllegalStateException("There is no context here...")
        val inflater = context.layoutInflater
        return AlertDialog.Builder(context)
            .setView(inflater.inflate(R.layout.filters, null))
            .setPositiveButton(R.string.filter) {
                dialogInterface, i ->  positiveButtonAction(dialogInterface, i)
            }
            .setNegativeButton(R.string.cancel) {
                dialogInterface, i -> negativeButtonAction(dialogInterface, i)
            }.create()
    }

    private fun negativeButtonAction(dialogInterface: DialogInterface, i: Int) {
        val searchFilter = SearchFilter(emptyList()) //TODO
        dialogSubject.onSuccess(searchFilter)
        dialogInterface.cancel()
    }

    private fun positiveButtonAction(dialogInterface: DialogInterface, i: Int) {
        dialogSubject.onComplete()
        dialogInterface.dismiss()
    }
}