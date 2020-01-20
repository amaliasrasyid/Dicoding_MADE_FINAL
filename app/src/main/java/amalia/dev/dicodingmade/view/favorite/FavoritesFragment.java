package amalia.dev.dicodingmade.view.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.TabPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initiation View
        //MUST USING getChilFragmentManager cause without it, when onResume() after onDestroy() views dissappear
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getActivity(), getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewpager_favorites);
        viewPager.setAdapter(tabPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tablayout_favorites);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

}
