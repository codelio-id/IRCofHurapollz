package com.jabirdeveloper.ircofhurapollz.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> juduls = new ArrayList<>();

    public void tambahFragment(Fragment fragment, String judul){
        fragments.add(fragment);
        juduls.add(judul);
    }

    public void hapusFragment (){
        fragments.clear();
        juduls.clear();
    }

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return juduls.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
