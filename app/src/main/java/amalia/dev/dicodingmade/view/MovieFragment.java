package amalia.dev.dicodingmade.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.viewmodel.MovieViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER ="section number";
    private final ArrayList<Movie> mdata = new ArrayList<>();
    private RecyclerView rvListMovies;
    private MovieAdapter adapter;


    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(int index){
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
//        mdata.addAll(getListMovies());
//        showRecylerList();
        adapter = new MovieAdapter(getActivity());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //viewmodel
        MovieViewModel movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                //Updating UI
                if(movies != null){
                    adapter.setData(movies);
                    rvListMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvListMovies.setAdapter(adapter);
                }
            }
        });
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        if(getArguments() != null){
//            int index =getArguments().getInt(ARG_SECTION_NUMBER);
//            if(index == 1){
//                final  RecyclerView rv = view.findViewById(R.id.rv_item);
//                MovieAdapter adapter = new MovieAdapter(getActivity(),getListTvShows());
//                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rv.setAdapter(adapter);
//            }
//
//        }
//    }


//    private void showRecylerList(){
//        MovieAdapter adapter = new MovieAdapter(getActivity(),mdata);
//
//        rvListMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //set adapter ke recyclerview
//        rvListMovies.setAdapter(adapter);
//    }



}
