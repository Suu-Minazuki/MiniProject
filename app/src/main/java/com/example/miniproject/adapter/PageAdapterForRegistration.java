package com.example.miniproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.miniproject.fragment.Registration1Fragment;
import com.example.miniproject.fragment.Registration2Fragment;

public class PageAdapterForRegistration extends FragmentStateAdapter {
    public PageAdapterForRegistration(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Registration1Fragment();
            case 1:
                return new Registration2Fragment();
            default:
                return new Registration1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
