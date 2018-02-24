package pl.brokenpipe.vozillatest.view.filters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pl.brokenpipe.vozillatest.view.filters.model.FilterItem

/**
 * Created by gwierzchanowski on 24.02.2018.
 */
class SubFilterAdapter(
        activity: Activity,
        val data: List<FilterItem>
) : ArrayAdapter<FilterItem>(activity, layId, data) {

    companion object {
        private const val layId = android.R.layout.simple_dropdown_item_1line
    }

    private val layoutInflater = activity.layoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder = SubFilterViewHolder()
        var view = convertView

        if (view == null) {
            view = layoutInflater.inflate(layId, null)
            viewHolder.label = view.findViewById(android.R.id.text1)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as SubFilterViewHolder
        }

        val item = getItem(position)
        viewHolder.label.text = item.label

        return view!!
    }



    inner class SubFilterViewHolder{
        lateinit var label: TextView
    }
}