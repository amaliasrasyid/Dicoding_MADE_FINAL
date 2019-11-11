package amalia.dev.dicodingmade;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER ="section number";
    private final ArrayList<Movie> mdata = new ArrayList<>();
    private RecyclerView rvListMovies;


    public MovieFragment() {
        // Required empty public constructor
    }

    static MovieFragment newInstance(int index){
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER,index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie,container,false);
        rvListMovies = view.findViewById(R.id.rv_item);
        rvListMovies.setHasFixedSize(true);
        mdata.addAll(getListMovies());
        showRecylerList();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            int index =getArguments().getInt(ARG_SECTION_NUMBER);
            if(index == 1){
                final  RecyclerView rv = view.findViewById(R.id.rv_item);
                MovieAdapter adapter = new MovieAdapter(getActivity(),getListTvShows());
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(adapter);
            }


        }
    }

    private ArrayList<Movie> getListMovies(){
        ArrayList<Movie> data = new ArrayList<>();
        String[] judulMovie = getResources().getStringArray(R.array.judul_film);
        String[] sinopsisMovie = getResources().getStringArray(R.array.sinopsis_film);
        String[] releaseDate = getResources().getStringArray(R.array.release_date);
        int[] posterName = {R.drawable.poster_mortalengine,R.drawable.poster_venom,R.drawable.poster_hunterkiller
                ,R.drawable.poster_birdbox,R.drawable.poster_dragon,R.drawable.poster_dragonball
                ,R.drawable.poster_robinhood,R.drawable.poster_spiderman,R.drawable.poster_thegirl,R.drawable.poster_themule};
        double[] rating = {7.7,7.7,6.8,6.5,8.1,7.7,7.7,6.8,6.5,8.1};

        //add data in form Movie object
        for(int i =0; i<=judulMovie.length-1;i++){
            data.add(new Movie(
                    judulMovie[i]
                    ,sinopsisMovie[i]
                    ,posterName[i]
                    ,releaseDate[i]
                    ,rating[i]
            ));
        }

        return data;
    }

    private ArrayList<Movie> getListTvShows(){
        ArrayList<Movie> data = new ArrayList<>();
        String[] judulTvshow = getResources().getStringArray(R.array.judul_tv_show);
        String[] sinopsisMovie = getResources().getStringArray(R.array.sinopsis_film);
        String[] releaseDate = getResources().getStringArray(R.array.release_date);
        int[] posterName = {R.drawable.poster_arrow,R.drawable.poster_doom_patrol,R.drawable.poster_gotham,R.drawable.poster_grey_anatomy
                            ,R.drawable.poster_hanna,R.drawable.poster_iron_fist,R.drawable.poster_naruto_shipudden,R.drawable.poster_ncis
                            ,R.drawable.poster_shameless,R.drawable.poster_the_umbrella};
        double[] rating = {5.8,6.5,6.9,6.4,6.7,6.1,7.6,6.3,5.9,7.7};

        //add data in form Movie object
        for(int i =0; i<=judulTvshow.length-1;i++){
            data.add(new Movie(
                    judulTvshow[i]
                    ,sinopsisMovie[i]
                    ,posterName[i]
                    ,releaseDate[i]
                    ,rating[i]
            ));
        }

        return data;
    }

    private void showRecylerList(){
        MovieAdapter adapter = new MovieAdapter(getActivity(),mdata);

        rvListMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        //set adapter ke recyclerview
        rvListMovies.setAdapter(adapter);
    }



}
