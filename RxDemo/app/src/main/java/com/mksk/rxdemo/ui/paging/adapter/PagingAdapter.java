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

public class PagingAdapter extends PagingDataAdapter<Datum, PagingAdapter.ViewHolder> {

    public PagingAdapter(){
        super(new DiffUtilDatum());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAilineBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum datum = getItem(position);

        if (datum != null) {
            holder.bind(datum);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAilineBinding binding;

//        public static ViewHolder from(ViewGroup parent) {
//            return
//        }

        public ViewHolder(@NonNull ItemAilineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Datum datum) {
            String id = String.valueOf(datum.getAirline().size());
            if (!datum.getAirline().isEmpty()) {
                id = String.valueOf(datum.getAirline().get(0).getName());
            }
            binding.tvId.setText(datum.get_id());
            binding.tvIDAirline.setText(id);
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
