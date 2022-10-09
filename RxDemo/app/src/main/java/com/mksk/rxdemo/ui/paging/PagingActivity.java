package com.mksk.rxdemo.ui.paging;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mksk.rxdemo.databinding.ActivityPaggingBinding;
import com.mksk.rxdemo.ui.paging.adapter.DataLoadStateAdapter;
import com.mksk.rxdemo.ui.paging.adapter.PagingAdapter;

public class PagingActivity extends AppCompatActivity implements PagingContract.View {

    private ActivityPaggingBinding binding;
    private PagingPresenter presenter;
    private PagingAdapter pagingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaggingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = PagingPresenter.getInstance(this);
        presenter.pagingDatum();
        init();

        presenter.data.observe(this, datumPagingData -> pagingAdapter.submitData(getLifecycle(), datumPagingData));

    }

    private void init() {
        pagingAdapter = new PagingAdapter();
        pagingAdapter.addLoadStateListener(loadStates -> {
            binding.sf.setRefreshing(loadStates.getSource().getRefresh() instanceof LoadState.Loading);
            return null;
        });

        binding.sf.setOnRefreshListener(() -> presenter.pagingDatum());

        binding.listSong.setAdapter(pagingAdapter.withLoadStateFooter(new DataLoadStateAdapter(() -> pagingAdapter.retry())));
        binding.listSong.setLayoutManager(new LinearLayoutManager(this));
    }
}