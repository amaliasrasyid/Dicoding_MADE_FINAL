package amalia.dev.dicodingmade.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.repository.sqlite.MovieHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment {
    private RecyclerView rv;
    private ArrayList<Movie> dataLocal = new ArrayList<>();
    private MovieHelper movieHelper; //for getting data from local database


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

        MovieAdapter adapter = new MovieAdapter(getActivity());

        //get data from local db
        movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
        movieHelper.open();
        dataLocal = movieHelper.getListFavMovies();

        //set data into adapter
        adapter.setData(dataLocal);
        //set adapter into rv
        rv.setAdapter(adapter);
        return view;
    }
    //don't forget sqlite must close after using it
    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    public void notifyMessage(String msg){
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        }

}
