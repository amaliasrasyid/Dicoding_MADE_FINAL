package amalia.dev.dicodingmade.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.SectionPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorites, container, false);


        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getActivity(),getActivity().getSupportFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewpager_favorites);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tablayout_favorites);
        tabLayout.setupWithViewPager(viewPager);
        if(getActivity().getActionBar() != null) {
             getActivity().getActionBar().setElevation(0);
        }
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        MovieFavFragment favFragment = new MovieFavFragment();
//        ft.replace(R.id.container_main,favFragment);
//        ft.commit();


        return view;
    }

}
