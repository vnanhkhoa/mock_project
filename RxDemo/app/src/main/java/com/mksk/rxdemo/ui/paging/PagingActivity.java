package com.mksk.rxdemo.ui.paging;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mksk.rxdemo.databinding.ActivityPaggingBinding;
import com.mksk.rxdemo.ui.paging.adapter.PagingAdapter;

public class PagingActivity extends AppCompatActivity implements PagingContract.View {

    private ActivityPaggingBinding binding;
    private PagingAdapter pagingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaggingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        PagingPresenter presenter = PagingPresenter.getInstance(this);
//
//        init();
//
//        presenter.datums.observe(this, datumPagingData -> pagingAdapter.submitData(getLifecycle(), datumPagingData));

    }

    private void init() {
        pagingAdapter = PagingAdapter.init();

        binding.listSong.setAdapter(pagingAdapter);
        binding.listSong.setLayoutManager(new LinearLayoutManager(this));
    }
}