package amalia.dev.consumerfavorites.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import amalia.dev.consumerfavorites.R;
import amalia.dev.consumerfavorites.view.movie.MovieFavFragment;
import amalia.dev.consumerfavorites.view.tvshow.TvShowFavFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    @StringRes
    private final  int[] TAB_TITLES = new int[]{
            R.string.title_tab1,
            R.string.title_tab2
    };

    public TabPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = new MovieFavFragment();
                break;
            case 1:
                fragment = new TvShowFavFragment();
                break;
        }
        return fragment; 
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
