package com.mksk.client.ui.menu.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mksk.client.R

@BindingAdapter("loadImage")
fun bindingImage(userImageView: ImageView, imageUri: String) {
    Glide.with(userImageView.context)
        .load(imageUri)
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.ic_round_error_24)
        .into(userImageView)
}