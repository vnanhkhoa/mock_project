package com.mksk.rxdemo.ui.paging.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mksk.rxdemo.data.network.response.Datum;
import com.mksk.rxdemo.databinding.ItemAilineBinding;

import java.util.ArrayList;

public class PagingAdapter extends PagingDataAdapter<Datum, PagingAdapter.ViewHolder> {
    private final ArrayList<Datum> data;

    public static PagingAdapter init() {
        return new PagingAdapter(new DiffUtilDatum());
    }

    private PagingAdapter(@NonNull DiffUtil.ItemCallback<Datum> diffCallback) {
        super(diffCallback);
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.from(parent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum datum = data.get(position);
        holder.bind(datum);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAilineBinding binding;

        public static ViewHolder from(ViewGroup parent) {
            return new ViewHolder(ItemAilineBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false
            ));
        }

        private ViewHolder(@NonNull ItemAilineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Datum datum) {
            binding.tvId.setText(datum.get_id());
            binding.tvIDAirline.setText(datum.getAirline().get(0).getId());
            binding.tvName.setText(datum.getName());
        }
    }

    static class DiffUtilDatum extends DiffUtil.ItemCallback<Datum> {

        @Override
        public boolean areItemsTheSame(@NonNull Datum oldItem, @NonNull Datum newItem) {
            return oldItem.get_id().equals(newItem.get_id());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Datum oldItem, @NonNull Datum newItem) {
            return oldItem.equals(newItem);
        }
    }
}
