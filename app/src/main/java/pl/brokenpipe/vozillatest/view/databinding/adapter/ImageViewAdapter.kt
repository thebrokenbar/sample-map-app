package pl.brokenpipe.vozillatest.view.databinding.adapter

import android.databinding.BindingAdapter
import android.support.v4.widget.ImageViewCompat
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by gwierzchanowski on 25.02.2018.
 */

@BindingAdapter("url")
fun ImageView.bindUrl(url: String) {
    Glide.with(this)
            .load(url)
            .into(this)
}