package com.mksk.rxdemo.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.mksk.rxdemo.utils.callback.CallBackLogic;

import java.util.List;

public class BaseDiffUtils<T> extends DiffUtil.Callback {

    private final List<T> old;
    private final List<T> news;
    private final CallBackLogic logic;

    public BaseDiffUtils(List<T> old, List<T> news, CallBackLogic logic) {
        this.old = old;
        this.news = news;
        this.logic = logic;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return news.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return logic.logic(oldItemPosition, newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.equals(news);
    }
}
