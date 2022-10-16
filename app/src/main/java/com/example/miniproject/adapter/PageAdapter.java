package com.example.miniproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.miniproject.fragment.AlumniFragment;
import com.example.miniproject.fragment.EventFragment;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new EventFragment();
            case 1:
                return new AlumniFragment();
            default:
                return  new EventFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
