package amalia.dev.dicodingmade.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.TvShow;
import amalia.dev.dicodingmade.viewmodel.MovieViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER ="section number";
    private final ArrayList<Movie> mdata = new ArrayList<>();
    private RecyclerView rvListMovies;
    private  MovieViewModel movieViewModel;
    ProgressBar progressBar;


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
        progressBar = view.findViewById(R.id.progress_circular_movie);

        rvListMovies.setHasFixedSize(true);
        return view;
    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        if(getArguments() != null){
//            int index = getArguments().getInt(ARG_SECTION_NUMBER);
//            if(index == 1){
//                //show tv show list
//                movieViewModel.getTvShows().observe(getViewLifecycleOwner(), new Observer<ArrayList<TvShow>>() {
//                    @Override
//                    public void onChanged(ArrayList<TvShow> tvShows) {
//                        //updating UI (load data and view)
//                        if(tvShows != null){
////                            adapter.setData(tvShows);
//                            showLoading(false);
//                            notifyMessage("list tv show");
//                        }else{
//                            showLoading(true);
//                        }
//                    }
//                });
//            }
//        }
//
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //viewmodel
        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                    //Updating UI
                    if (movies != null) {
                        showLoading(false);
                        MovieAdapter adapter = new MovieAdapter(getActivity());
                        adapter.setData(movies);
                        rvListMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvListMovies.setAdapter(adapter);
                    }else {
                        showLoading(true);
                    }
                }
            });
    }

    public void notifyMessage(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
