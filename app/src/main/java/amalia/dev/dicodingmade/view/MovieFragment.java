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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.viewmodel.MovieViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private final ArrayList<Movie> mdata = new ArrayList<>();
    private RecyclerView rvListMovies;
    ProgressBar progressBar;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie,container,false);
        rvListMovies = view.findViewById(R.id.rv_movie_fragment);
        progressBar = view.findViewById(R.id.progress_circular_movie);

        rvListMovies.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instance viewmodel movie
        MovieViewModel movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(ArrayList<Movie> movies) {
                    //Updating UI
                    if (movies != null) {
                        showLoading(false);
                        MovieAdapter adapter = new MovieAdapter(getActivity());
                        //set data dg nilai yang didapat dr db TMDB
                        adapter.setData(movies);
                        rvListMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvListMovies.setAdapter(adapter);
                    }else {
                        //when fetching data, show indicator loading
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
