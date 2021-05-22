package com.alhudaghifari.androidbasicsub

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {
    fun loadImage(context: Context, ivImage: ImageView, posterPath: String?) {
        Glide
            .with(context)
            .load(posterPath)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(ivImage)
    }
}