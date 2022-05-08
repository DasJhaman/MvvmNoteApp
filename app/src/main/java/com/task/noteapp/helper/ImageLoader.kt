package com.task.noteapp.helper

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun <T> ImageView.loadImage(
    model: T
) {
    Glide.with(context)
        .load(model)
        .apply(
            RequestOptions.diskCacheStrategyOf(
                DiskCacheStrategy.NONE))
        .into(this)
}
