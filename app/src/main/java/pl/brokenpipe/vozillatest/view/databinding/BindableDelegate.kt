package pl.brokenpipe.vozillatest.view.databinding

import android.databinding.BaseObservable
import kotlin.properties.Delegates

/**
 * Created by gwierzchanowski on 25.02.2018.
 */
fun <T> BaseObservable.bindable(default: T, beforeNotify: ((newValue: T) -> Unit)? = null) =
        Delegates.observable(default) { property, _, newValue ->
            val id = BindingResourceMapper.getBindableResourceIdByPropertyName(property.name)
            beforeNotify?.invoke(newValue)
            this.notifyPropertyChanged(id)
        }