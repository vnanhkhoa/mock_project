package com.mksk.rxdemo.ui.main;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.mksk.rxdemo.R;
import com.mksk.rxdemo.databinding.ActivityMainBinding;
import com.mksk.rxdemo.ui.main.adapter.PageAdapter;
import com.mksk.rxdemo.ui.main.bookmark.BookmarkFragment;
import com.mksk.rxdemo.ui.main.genre.GenreFragment;
import com.mksk.rxdemo.ui.main.home.HomeFragment;
import com.mksk.rxdemo.ui.main.other.OtherFragment;
import com.mksk.rxdemo.ui.main.update.UpdateFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Fragment> fragments;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragment();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PageAdapter pagingAdapter = new PageAdapter(MainActivity.this, fragments);
        binding.viewPager2.setAdapter(pagingAdapter);
        binding.viewPager2.setOffscreenPageLimit(2);

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int id;
                switch (position) {
                    case 1:
                        id = R.id.bookmark;
                        break;
                    case 2:
                        id = R.id.genre;
                        break;
                    case 3:
                        id = R.id.update;
                        break;
                    case 4:
                        id = R.id.other;
                        break;
                    default:
                        id = R.id.home;
                }

                binding.bottomNavigationView.setSelectedItemId(id);
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int page;
            switch (item.getItemId()) {
                case R.id.bookmark:
                    page = 1;
                    break;
                case R.id.genre:
                    page = 2;
                    break;
                case R.id.update:
                    page = 3;
                    break;
                case R.id.other:
                    page = 4;
                    break;
                default:
                    page = 0;
            }

            binding.viewPager2.setCurrentItem(page);
            return false;
        });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new BookmarkFragment());
        fragments.add(new GenreFragment());
        fragments.add(new UpdateFragment());
        fragments.add(new OtherFragment());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int page = binding.viewPager2.getCurrentItem();
        if (page > 0) {
            binding.viewPager2.setCurrentItem(page - 1);
        }
    }

    public void visibleBottomNavigation(int visible) {
        boolean inputUser = visible == View.VISIBLE;
        binding.bottomNavigationView.setVisibility(visible);
        binding.viewPager2.setUserInputEnabled(inputUser);
    }

    public void fullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void exitFullScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
    }
}