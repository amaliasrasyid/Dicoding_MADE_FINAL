package amalia.dev.dicodingmade.view.tvshow;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.TvShowAdapter;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import amalia.dev.dicodingmade.model.TvShowResult;
import amalia.dev.dicodingmade.view.search.SearchResultsActivity;
import amalia.dev.dicodingmade.viewmodel.TvShowViewModel;

import static amalia.dev.dicodingmade.view.search.SearchResultsActivity.EXTRA_SEARCH_RESULTS;
import static amalia.dev.dicodingmade.view.search.SearchResultsActivity.TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvListTv;
    private ProgressBar progressBar;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show,container,false);
        rvListTv = view.findViewById(R.id.rv_tvshow_fragment);
        progressBar = view.findViewById(R.id.progress_circular_tvshow);



        rvListTv.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instance viewmodel tvshow
        TvShowViewModel tvShowViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(this, new Observer<TvShowResult>() {
            @Override
            public void onChanged(TvShowResult tvShowResult) {
                if(tvShowResult != null){
                    showLoading(false);
                    ArrayList<TvShowRealmObject> dataListTv = new ArrayList<>(tvShowResult.getTvShowsResults());
                    TvShowAdapter adapter = new TvShowAdapter(getActivity());
                    adapter.setData(dataListTv);
                    rvListTv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvListTv.setAdapter(adapter);
                }else{
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
                intent.putExtra(TYPE,"tvshow");
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

    public void notifyMessage(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

}
