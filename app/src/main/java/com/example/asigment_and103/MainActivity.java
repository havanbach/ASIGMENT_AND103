package com.example.asigment_and103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.asigment_and103.Adapter.ViewpagerAdapter;
import com.example.asigment_and103.Interfece.FragmentReload;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
        ViewPager viewPager;
        BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpaper);
        bottomNavigationView = findViewById(R.id.bottomnav);

        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                FragmentReload fragmentReload = (FragmentReload) viewpagerAdapter.instantiateItem(viewPager,position);
                switch (position){
                    case 0: bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true); break;
                    case 1: bottomNavigationView.getMenu().findItem(R.id.search).setChecked(true); break;
                    case 2: bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true); break;
                    case 3: bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if (itemID == R.id.home){
                    viewPager.setCurrentItem(0);
                }else if(itemID== R.id.search){
                    viewPager.setCurrentItem(1);
                }else  if(itemID == R.id.cart){
                    viewPager.setCurrentItem(2);
                }else if(itemID==R.id.profile){
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });

    }
}