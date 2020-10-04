package com.jabirdeveloper.ircofhurapollz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.MainActivity;
import com.jabirdeveloper.ircofhurapollz.adapter.TabAdapter;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;

public class HomeFragment extends Fragment {

    public ViewPager viewPager;
    private MainActivity mainActivity;
    private TabAdapter tabAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        mainActivity = (MainActivity) getActivity();
        tabAdapter = new TabAdapter(getParentFragmentManager());
        TabLayout tabLayout = root.findViewById(R.id.main_tablayout);
        viewPager = root.findViewById(R.id.main_viewpager);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setupFragment();

        mainActivity.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(0);
                    return true;
                }
                case R.id.nav_cerpen: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(1);
                    return true;
                }
                case R.id.nav_history: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(2);
                    return true;
                }
                case R.id.nav_puisi: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(3);
                    return true;
                }
                case R.id.nav_creepypasta: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(4);
                    return true;
                }
                case R.id.nav_knowledge: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(5);
                    return true;
                }
                case R.id.nav_riddle: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(6);
                    return true;
                }
                case R.id.nav_others: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    viewPager.setCurrentItem(7);
                    return true;
                }
                default: {
                    mainActivity.drawer.closeDrawer(GravityCompat.START);
                    return false;
                }
            }

        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_home);
                        break;
                    case 2:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_cerpen);
                        break;
                    case 3:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_history);
                        break;
                    case 4:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_puisi);
                        break;
                    case 5:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_creepypasta);
                        break;
                    case 6:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_knowledge);
                        break;
                    case 7:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_riddle);
                        break;
                    case 8:
                        mainActivity.navigationView.setCheckedItem(R.id.nav_others);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setupFragment() {
        for (int i = 0; i < AppConfig.KATEGORI_LIST.size(); i++) {
            Bundle b = new Bundle();
            TabKategoriFragment kategoriFragment = new TabKategoriFragment();
            if (i == 0) {
                tabAdapter.tambahFragment(new TabTerbaruFragment(), AppConfig.KATEGORI_LIST.get(i).getKategori());
            } else {
                b.putString("id", AppConfig.KATEGORI_LIST.get(i).getId());
                kategoriFragment.setArguments(b);
                tabAdapter.tambahFragment(kategoriFragment, AppConfig.KATEGORI_LIST.get(i).getKategori());
            }
            tabAdapter.notifyDataSetChanged();
        }
    }

}