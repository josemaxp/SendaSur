package josemanuel.marin.sendasur.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import josemanuel.marin.sendasur.ui.MainActivity;
import josemanuel.marin.sendasur.ui.TabFavoritesFragment;
import josemanuel.marin.sendasur.ui.TabMapFragment;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(MainActivity mainActivity) {
        super(mainActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new TabMapFragment();
            case 1: return new TabFavoritesFragment();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
