package com.avv.benmesabe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by angelvazquez on 2/11/15.
 */
public class MenuFragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] =
            new String[] {"Sugerencias", "Menú", "Pedido"};

    public MenuFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch(position) {
            case 0:
                f = SuggestionsFragment.newInstance();
                break;
            case 1:
                f = MenuFragment.newInstance();
                break;
            case 2:
                //f = ScanFragment.newInstance();
                f = OrderFragment.newInstance();
                break;
        }

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
