package amalia.dev.dicodingmade.view.tvshow;

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
import amalia.dev.dicodingmade.model.TvShow;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW ="extra tv show";
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w154";
    private static final String BASE_URL_BACK_POSTER = "https://image.tmdb.org/t/p/w500";
    private Realm realm;
    private RealmHelper realmHelper;
    private TvShow tvShow = new TvShow();
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        //binding
        TextView popularity = findViewById(R.id.tv_tvdetail_popularity);
        TextView freleaseDate = findViewById(R.id.tv_tvdetail_releasedata);
        TextView overview = findViewById(R.id.tv_tvdetail_sinopsis);
        TextView genres = findViewById(R.id.tv_tvdetail_genres);
        ImageView poster = findViewById(R.id.img_tvdetail_poster);
        TextView title = findViewById(R.id.tv_tvdetail_judul);
        TextView rating = findViewById(R.id.tv_tvdetail_rating);
        ImageView backPoster = findViewById(R.id.img_tvdetail_backposter);
        ProgressBar pbBackPoster = findViewById(R.id.progressBar_tvdetail_backposter);
        ProgressBar pbPoster = findViewById(R.id.progressBar_tvdetail_poster);

        //Realm configuratio
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        //getting data from the objek that clicked in list
        tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        //get genre's name based the id
        List<Integer> genresId = tvShow.getGenreIds();
        if(genresId != null){
            String genresName = getGenresName(genresId);
            genres.setText(genresName);
        }


        //binding view & data
        popularity.setText(String.valueOf(tvShow.getPopularity()));
        rating.setText(String.valueOf(tvShow.getVoteAverage()));
        overview.setText(tvShow.getOverview());
        title.setText(tvShow.getOriginalName());
        freleaseDate.setText(convertToDatePattern(tvShow.getFirstAirDate()));

        Glide.with(this).load(BASE_URL_POSTER +tvShow.getPosterPath())
                .listener(showLoading(pbPoster))
                .transform(new RoundedCorners(15))
                .into(poster);
        Glide.with(this).load(BASE_URL_BACK_POSTER +tvShow.getBackdropPath())
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
        if(isCheckedFav(tvShow.getId())){
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
                addFavorite(tvShow);
            return true;
            case R.id.menu_fav_checked:
                item.setVisible(false);
                menu.getItem(0).setVisible(true);
                deleteFavorite(tvShow.getId());
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteFavorite(int id) {
        realmHelper.deleteFavTvShow(id);
    }

    private void addFavorite(TvShow tvShow) {
        realmHelper.insertTvShow(tvShow);
    }

    private boolean isCheckedFav(int id) {
        return  realmHelper.isTvShowExist(id);
    }


    private String getGenresName(List<Integer> genresId) {
        List<String> genresName = new ArrayList<>();

        for (int j=0;j<genresId.size();j++){
            int value = genresId.get(j);
            //search value that same
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

        SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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