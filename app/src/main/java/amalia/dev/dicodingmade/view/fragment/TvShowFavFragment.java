package amalia.dev.dicodingmade.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.TvShow;
/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment {
    RecyclerView rv;
    ArrayList<TvShow> dataLocal = new ArrayList<>();


    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
        rv = view.findViewById(R.id.rv_tvshow_fav_fragment);
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv.setHasFixedSize(true);
//        loadDataLocal();//loading data from local storage
//
//        TvShowAdapter adapter = new TvShowAdapter(getActivity());
//        adapter.setData(dataLocal);
//        rv.setAdapter(adapter);
        return view;
    }
//
//    private void loadDataLocal(){
//        //get list tv show fav from local storage
//        realmHelper = new RealmHelper(realm);
//        List<TvShow> tvShowList = realmHelper.getListFavoriteTvShows();
//        //insert all list data into arraylist
//        dataLocal.addAll(tvShowList);
//    }

}
