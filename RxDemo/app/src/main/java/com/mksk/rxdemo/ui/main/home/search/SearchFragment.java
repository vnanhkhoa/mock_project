package com.mksk.rxdemo.ui.main.home.search;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mksk.rxdemo.databinding.FragmentSearchBinding;
import com.mksk.rxdemo.ui.main.MainActivity;
import com.mksk.rxdemo.ui.main.home.HomeFragment;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private MediaController mediaController;
    private HomeFragment homeFragment;
    private MainActivity mainActivity;
    private boolean isFullScreen = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaController = new MediaController(requireContext());

        homeFragment = (HomeFragment) requireParentFragment().requireParentFragment();
        mainActivity = (MainActivity) requireActivity();
        requireActivity().getOnBackPressedDispatcher().addCallback(this,new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isFullScreen) {
                    setDefault();
                    return;
                }

                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpVideo();

        binding.btn.setOnClickListener(v -> {
            mainActivity.fullScreen();
            binding.guideline2.setGuidelinePercent(1);
            binding.getRoot().setBackgroundColor(BLACK);
            homeFragment.toolBarVisible(GONE);
            mainActivity.visibleBottomNavigation(GONE);
            binding.btnClose.setVisibility(VISIBLE);
            isFullScreen = true;
        });

        binding.btnClose.setOnClickListener(v -> setDefault());
    }

    private void setUpVideo() {
        try {
            mediaController.setAnchorView(binding.video);
            binding.video.setMediaController(mediaController);
            Uri uri = Uri.parse("https://cucumber.fsoft.com.vn/wp-content/uploads/sites/14/2022/06/Fsoft_Cuba_4_minimal.mp4");
            binding.video.setVideoURI(uri);
            binding.video.setOnPreparedListener(mediaPlayer -> {
                mediaController.show();
                mediaController.requestFocus();
                binding.loading.setVisibility(GONE);
            });
        } catch (Exception e) {
            Log.e("TAG", "setUpVideo: " + e.getMessage());
            Toast.makeText(requireContext(), "Play video error", Toast.LENGTH_LONG).show();
        }
    }

    private void setDefault() {
        mainActivity.exitFullScreen();
        homeFragment.toolBarVisible(VISIBLE);
        mainActivity.visibleBottomNavigation(VISIBLE);
        binding.getRoot().setBackgroundColor(WHITE);
        binding.btnClose.setVisibility(GONE);
        binding.guideline2.setGuidelinePercent(0.35f);
        isFullScreen = false;
    }
}