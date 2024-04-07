package com.example.asigment_and103.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asigment_and103.FragmentCart.Cart;
import com.example.asigment_and103.FragmentHome.Home;
import com.example.asigment_and103.FragmentProfile.Profile;
import com.example.asigment_and103.FragmentSearch.Search;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {

    public ViewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new  Home();
            case 1 : return new Search();
            case 2 : return new Cart();
            case 3 :return new Profile();
            default: return new Home();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
