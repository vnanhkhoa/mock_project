package com.mksk.rxdemo.ui.main.home.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mksk.rxdemo.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private MediaController mediaController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaController = new MediaController(requireContext());
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
           Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
           Uri data = Uri.parse("https://cucumber.fsoft.com.vn/wp-content/uploads/sites/14/2022/06/Fsoft_Cuba_4_minimal.mp4");
           intent.setData(data);
           startActivity(intent);
       });
    }

    private void setUpVideo() {
        try {
            mediaController.setAnchorView(binding.video);
            binding.video.setMediaController(mediaController);
            Uri uri = Uri.parse("https://cucumber.fsoft.com.vn/wp-content/uploads/sites/14/2022/06/Fsoft_Cuba_4_minimal.mp4");
            binding.video.setVideoURI(uri);
            binding.video.setOnPreparedListener(mediaPlayer -> {
                mediaPlayer.start();
                mediaController.requestFocus();
                binding.loading.setVisibility(View.GONE);
            });
        } catch (Exception e) {
            Log.e("TAG", "setUpVideo: "+ e.getMessage());
            Toast.makeText(requireContext(),"Play video error",Toast.LENGTH_LONG).show();
        }
    }
}