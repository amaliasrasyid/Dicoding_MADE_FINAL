package amalia.dev.dicodingmade.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.Genre;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import amalia.dev.dicodingmade.repository.sqlite.MovieHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;


public class MovieDetailActivity extends AppCompatActivity{
    public static final String EXTRA_MOVIE ="extra movie";
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w154";
    private static final String BASE_URL_BACK_POSTER = "https://image.tmdb.org/t/p/w500";
    private Menu menu;// Global Menu Declaration
    private Movie movie = new Movie();
    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        TextView popularity = findViewById(R.id.tv_moviedetail_popularity);
        TextView releaseDate = findViewById(R.id.tv_moviedetail_releasedata);
        TextView overview = findViewById(R.id.tv_moviedetail_overview);
        TextView genres = findViewById(R.id.tv_moviedetail_genres);
        ImageView poster = findViewById(R.id.img_moviedetail_poster);
        TextView title = findViewById(R.id.tv_moviedetail_judul);
        TextView rating = findViewById(R.id.tv_moviedetail_rating);
        ImageView backPoster = findViewById(R.id.img_moviedetail_backposter);
        ProgressBar pbBackPoster = findViewById(R.id.progressBar_moviedetail_backposter);
        ProgressBar pbPoster = findViewById(R.id.progressBar_moviedetail_poster);

        //database local
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelper(realm);

        //getting data from the objek that clicked in list
         movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        //get genre's name based the id
        List<Integer> genresId =  movie.getGenreIds();
        if(genresId != null){
            String genresName = getGenresName(genresId);
            genres.setText(genresName);
        }




        //binding view & data

        popularity.setText(String.valueOf(movie.getPopularity()));
        rating.setText(String.valueOf(movie.getVoteAverage()));
        overview.setText(movie.getOverview());
        title.setText(movie.getTitle());
        releaseDate.setText(convertToDatePattern(movie.getReleaseDate()));



        Glide.with(this).load(BASE_URL_POSTER +movie.getPosterPath())
                .listener(showLoading(pbPoster))
                .transform(new RoundedCorners(15))
                .into(poster);
        Glide.with(this).load(BASE_URL_BACK_POSTER +movie.getBackdropPath())
                .listener(showLoading(pbBackPoster))
                .into(backPoster);


        //settin up button
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_fav,menu);
        if(isCheckedFav(movie.getId())){
            menu.findItem(R.id.menu_fav_unchecked).setVisible(false);
            menu.findItem(R.id.menu_fav_checked).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fav_unchecked:
                item.setVisible(false);
                menu.getItem(1).setVisible(true);
                addFavorite(movie);
            return true;
            case R.id.menu_fav_checked:
                item.setVisible(false);
                menu.getItem(0).setVisible(true);
                deleteFavorite(movie.getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deleteFavorite(Integer id) {
        realmHelper.deleteFavMovies(id);
    }

    private void addFavorite(Movie movie) {
        realmHelper.insertMovie(movie);
    }

    private boolean isCheckedFav(int id) {
        return  realmHelper.isMovieExist(id);
    }

    private String getGenresName(List<Integer> genresId) {
        List<String> genresName = new ArrayList<>();

        for (int j=0;j<genresId.size();j++){
            int value = genresId.get(j);
            //search name genre based the id
            realmHelper = new RealmHelper(realm);
            genresName.add(realmHelper.getGenreName(value));
        }
        //convert list<String> to string
        StringBuilder sb = new StringBuilder();
        for(String s:genresName){
            sb.append(s);
            sb.append("\t\t");
        }

        return sb.toString();
    }


    private String convertToDatePattern(String releaseDate) {
        //create date pattern format
        //Locale.getDefault() get current Language android for format date
        SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat toString = new SimpleDateFormat("dd MMMM, yyyy",Locale.getDefault());
        Date date;
        String str = "";
        try {
            //parse string to date
            date = toDate.parse(releaseDate);
            //convert date into string with a format pattern
             str =toString.format(date);
            return  str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private RequestListener<Drawable> showLoading(final ProgressBar progressBar) {
        return new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //show progress bar when loading data
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
