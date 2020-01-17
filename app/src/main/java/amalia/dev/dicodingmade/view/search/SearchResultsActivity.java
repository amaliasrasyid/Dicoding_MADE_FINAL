package amalia.dev.dicodingmade.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.adapter.TvShowAdapter;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.model.MovieResult;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import amalia.dev.dicodingmade.model.TvShowResult;
import amalia.dev.dicodingmade.repository.api.ApiInterface;
import amalia.dev.dicodingmade.repository.api.ApiRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchResultsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    public static final String EXTRA_SEARCH_RESULTS = "searchResults";
    public static final String TYPE = "type";
    ProgressBar progressBar;
    RecyclerView rv;
    ImageView imgNotFound;
    TextView tvNotFound;
    MovieAdapter movieAdapter;
    TvShowAdapter tvShowAdapter;
    ArrayList<TvShowRealmObject> resultTvshow;
    ArrayList<MovieRealmObject> resultMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        imgNotFound = findViewById(R.id.image_searchresult_notfound);
        tvNotFound = findViewById(R.id.text_searchresult_notfound);
        progressBar = findViewById(R.id.progress_circular_searchresults);
        rv = findViewById(R.id.recyclerview_searchresults);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));



        Intent intent = getIntent();
        String type = intent.getStringExtra(TYPE);
        if (type != null) {
            if (type.equals("movie")) {
                movieAdapter = new MovieAdapter(this);
                resultMovie = new ArrayList<>();
            } else if (type.equals("tvshow")) {
                tvShowAdapter = new TvShowAdapter(this);
                resultTvshow = new ArrayList<>();
            }
            loadQuerySearch(intent.getStringExtra(EXTRA_SEARCH_RESULTS),type);
        }

        //listen for change in the back stack
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        //settin up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//change name in toolbar
            getSupportActionBar().setTitle("Results");
        }
    }

    @Override
    public void onBackStackChanged() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. just finish the actiivity
        finish();
        return true;
    }

    private void loadQuerySearch(final String query, String type) {
        Retrofit retrofit = ApiRepository.getInstance();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String apiKey = BuildConfig.TMDB_API_KEY;

        if (type.equals("movie")) {
            Call<MovieResult> movieResultCall = apiInterface.searchMovies(apiKey, query);
            movieResultCall.enqueue(new Callback<MovieResult>() {
                MovieResult result = new MovieResult();

                @Override
                public void onResponse(@Nullable Call<MovieResult> call, @Nullable Response<MovieResult> response) {
                    if (response != null && response.isSuccessful()) {
                        showLoading();
                        result = response.body();
                        if (result != null) {
                            resultMovie.addAll(result.getMoviesResults());
                            movieAdapter.setData(resultMovie);
                            rv.setAdapter(movieAdapter);
                            if(resultMovie.size() == 0) {
                                imgNotFound.setVisibility(View.VISIBLE);
                                tvNotFound.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                }

                @Override
                public void onFailure(@Nullable Call<MovieResult> call, @Nullable Throwable t) {
                    if (t != null) {
                        Log.e("Load Result Movies ",t.toString());
                    }
                }
            });
        } else if (type.equals("tvshow")) {
            Call<TvShowResult> tvShowResultCall = apiInterface.searchTvshows(apiKey,query);
            tvShowResultCall.enqueue(new Callback<TvShowResult>() {
                TvShowResult result;
                @Override
                public void onResponse(@Nullable Call<TvShowResult> call, @Nullable Response<TvShowResult> response) {
                    if(response != null && response.isSuccessful()){
                        showLoading();
                        result = response.body();
                        if (result != null) {
                            resultTvshow.addAll(result.getTvShowsResults());
                            tvShowAdapter.setData(resultTvshow);
                            rv.setAdapter(tvShowAdapter);
                            if(resultTvshow.size() == 0) {
                                imgNotFound.setVisibility(View.VISIBLE);
                                tvNotFound.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@Nullable Call<TvShowResult> call, @Nullable Throwable t) {
                    if (t != null) {
                        Log.e("Load Result Tvshows",t.toString());
                    }
                }
            });
        }

    }

    private void showLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
