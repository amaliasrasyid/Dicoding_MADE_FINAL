package amalia.dev.dicodingmade.view.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorites, container, false);


        //MUST USING getChilFragmentManager cause without it, when onResume() after onDestroy() views dissappear
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getActivity(),getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewpager_favorites);
        viewPager.setAdapter(tabPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tablayout_favorites);
        tabLayout.setupWithViewPager(viewPager);
        if(getActivity().getActionBar() != null) {
             getActivity().getActionBar().setElevation(0);
        }

        return view;
    }


    public void notifyMessage(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
