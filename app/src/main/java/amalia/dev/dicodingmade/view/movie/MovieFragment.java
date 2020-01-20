package amalia.dev.dicodingmade.view.movie;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.model.MovieResult;
import amalia.dev.dicodingmade.view.search.SearchResultsActivity;
import amalia.dev.dicodingmade.viewmodel.MovieViewModel;

import static amalia.dev.dicodingmade.view.search.SearchResultsActivity.EXTRA_SEARCH_RESULTS;
import static amalia.dev.dicodingmade.view.search.SearchResultsActivity.TYPE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvListMovies;
    private ProgressBar progressBar;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initiation View (create reference view)
        rvListMovies = view.findViewById(R.id.recyclerview_movie);
        progressBar = view.findViewById(R.id.progress_circular_movie);
        rvListMovies.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instance viewmodel movie
//        MovieViewModel movieViewModel = new MovieViewModel(getActivity().getApplication());
        MovieViewModel movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, new Observer<MovieResult>() {
            @Override
            public void onChanged(MovieResult movieResult) {
                if (movieResult != null) {
                    showLoading(false);
                    ArrayList<MovieRealmObject> dataListMovies = new ArrayList<>(movieResult.getMoviesResults());
                    MovieAdapter adapter = new MovieAdapter(getActivity());
                    adapter.setData(dataListMovies);
                    rvListMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvListMovies.setAdapter(adapter);
                } else {
                    showLoading(true);
                }

            }
        });
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflate menu search
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // perform set on query text listener event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //pass query to results activity
                Intent intent  = new Intent(getActivity(), SearchResultsActivity.class);
                intent.putExtra(EXTRA_SEARCH_RESULTS,query);
                intent.putExtra(TYPE,"movie");
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
