package com.mksk.rxdemo.ui.paging.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mksk.rxdemo.databinding.ItemStateBinding;

public class DataLoadStateAdapter extends LoadStateAdapter<DataLoadStateAdapter.ViewHolder> {

    private final ListenerRetry listenerRetry;

    public DataLoadStateAdapter(ListenerRetry listenerRetry) {
        this.listenerRetry = listenerRetry;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull LoadState loadState) {
        viewHolder.onBind(loadState);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new ViewHolder(
                ItemStateBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false)
        );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemStateBinding binding;

        public ViewHolder(@NonNull ItemStateBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void onBind(LoadState state) {

            if (state instanceof LoadState.Error) {
                binding.tvError.setText(((LoadState.Error) state).getError().getMessage());
                binding.group.setVisibility(View.VISIBLE);
                binding.loading.setVisibility(View.GONE);
                binding.button.setOnClickListener(v -> listenerRetry.retry());
                return;
            }
            
            if (state instanceof LoadState.Loading) {
                binding.group.setVisibility(View.GONE);
                binding.loading.setVisibility(View.VISIBLE);
            }

            
        }
    }
}
