package com.mksk.rxdemo.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mksk.rxdemo.ui.main.adapter.PageAdapter;

public class CustomDataBinding {

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView).asDrawable().load(url).into(imageView);
    }

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView view, PageAdapter pageAdapter) {
        view.setAdapter(pageAdapter);
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}
