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
import amalia.dev.dicodingmade.repository.sqlite.MovieHelper;


public class MovieDetailActivity extends AppCompatActivity{
    public static final String EXTRA_MOVIE ="extra movie";
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w154";
    private static final String BASE_URL_BACK_POSTER = "https://image.tmdb.org/t/p/w500";
    private final ArrayList<Genre>  genreData= new ArrayList<>();
    private Menu menu;// Global Menu Declaration
    private Movie movie = new Movie();
    MovieHelper movieHelper;

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

        //getting data from the objek that clicked in list
         movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

//        //get genre's name based the id
//        loadGenreData();
//        List<Integer> genresId = movie.getGenreIds();
//        String genresName = getGenresName(genresId);


        //binding view & data
//        genres.setText(genresName);
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

        movieHelper = MovieHelper.getInstance(this);
        movieHelper.open();

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
        movieHelper.deleteFavMovie(String.valueOf(id));
    }

    private void addFavorite(Movie movie) {
        movieHelper.insertFavMovie(movie);
    }

    private boolean isCheckedFav(int id) {
      return  movieHelper.isStored(String.valueOf(id));
    }

    private String getGenresName(List<Integer> genresId) {
        List<String> genresName = new ArrayList<>();

        for (int j=0;j<genresId.size();j++){
            int value = genresId.get(j);
            //search value that same
            for (int i=0;i<genreData.size();i++){
                if(value == genreData.get(i).getId()){
                    genresName.add(genreData.get(i).getName());
                }
            }
        }
        //convert list<String> to string
        StringBuilder sb = new StringBuilder();
        for(String s:genresName){
            sb.append(s);
            sb.append("\t\t");
        }

        return sb.toString();
    }

    private void loadGenreData(){
        int[] genreIdData = {28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648};
        String[] genreNameData = getResources().getStringArray(R.array.genre_name);

        for (int i=0;i<genreIdData.length;i++){
            //create new object genre
            Genre genre = new Genre(genreIdData[i],genreNameData[i]);
            //insert into arraylist
            genreData.add(genre);
        }
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
        movieHelper.close();
    }
}
