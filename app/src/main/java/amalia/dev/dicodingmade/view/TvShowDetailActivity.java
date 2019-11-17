package amalia.dev.dicodingmade.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.TvShow;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW ="extra movie";
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w154";
    private static final String BASE_URL_BACK_POSTER = "https://image.tmdb.org/t/p/w500";
    TextView popularity,freleaseDate,overview,judul,rating;
    ImageView poster,backPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        //binding
        popularity = findViewById(R.id.tv_tvdetail_popularity);
        freleaseDate = findViewById(R.id.tv_tvdetail_releasedata);
        overview = findViewById(R.id.tv_tvdetail_sinopsis);
        poster = findViewById(R.id.img_tvdetail_poster);
        judul = findViewById(R.id.tv_tvdetail_judul);
        rating = findViewById(R.id.tv_tvdetail_rating);
        backPoster = findViewById(R.id.img_tvdetail_backposter);

        //getting data from the objek that clicked in list
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        //binding view & data
        popularity.setText(String.valueOf(tvShow.getPopularity()));
        rating.setText(String.valueOf(tvShow.getVoteAverage()));
        freleaseDate.setText(tvShow.getFirstAirDate());
        overview.setText(tvShow.getOverview());
        judul.setText(tvShow.getOriginalName());

        Glide.with(this).load(BASE_URL_POSTER +tvShow.getPosterPath()).into(poster);
        Glide.with(this).load(BASE_URL_BACK_POSTER +tvShow.getBackdropPath()).into(backPoster);

    }
}
