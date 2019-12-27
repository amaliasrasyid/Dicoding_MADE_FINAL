package amalia.dev.dicodingmade.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.adapter.MovieFavAdapter;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import amalia.dev.dicodingmade.repository.sqlite.MovieHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment{
    private RecyclerView rv;
    private ArrayList<Movie> dataLocal = new ArrayList<>();
    Realm realm;
    RealmHelper realmHelper;
    RealmChangeListener realmChangeListener;
    MovieFavAdapter adapter;



    public MovieFavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);



        rv = view.findViewById(R.id.rv_movie_fav_fragment);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);

        //get data from local db
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelper(realm);

        adapter = new MovieFavAdapter(getActivity(),realmHelper.getListFavoriteMovies());

        //set data into adapter
        //set adapter into rv
        rv.setAdapter(adapter);


        refresh();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        this.menu = menu;
//        this.menuInflater = inflater;
        adapter.setMenu(menu);
        adapter.setInflater(inflater);
    }


    //when there's change on data, do refresh
    void refresh(){
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
               adapter = new MovieFavAdapter(getActivity(),realmHelper.getListFavoriteMovies());
                rv.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    //don't forget realm must close after using it
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void notifyMessage(String msg){
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        }



}
