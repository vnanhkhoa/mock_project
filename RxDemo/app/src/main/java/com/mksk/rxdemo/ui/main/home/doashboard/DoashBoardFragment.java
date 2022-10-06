package com.mksk.rxdemo.ui.main.home.doashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mksk.rxdemo.databinding.FragmentDoashBoardBinding;

public class DoashBoardFragment extends Fragment {

    private FragmentDoashBoardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoashBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btn.setOnClickListener(view1 -> {
            NavDirections action = DoashBoardFragmentDirections.actionDoashBoardFragmentToSearchFragment();
            Navigation
                    .findNavController(view)
                    .navigate(action);
        });
    }
}