package com.mksk.rxdemo.ui.main.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.mksk.rxdemo.R;
import com.mksk.rxdemo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;
    private AppCompatActivity activity;
    private final MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            if (menuItem.getItemId() == android.R.id.home) {
                navController.popBackStack();
            }
            return true;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) requireActivity();
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(menuProvider);
        requireActivity().getOnBackPressedDispatcher().addCallback(this,new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navController.popBackStack();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        assert navController != null;
        NavigationUI.setupActionBarWithNavController(activity,navController);

    }

    public void toolBarVisible(int visible) {
        binding.toolbar.setVisibility(visible);
    }

}