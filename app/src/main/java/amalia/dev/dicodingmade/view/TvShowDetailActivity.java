package amalia.dev.dicodingmade.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.TvShow;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW ="extra tv show";
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w154";
    private static final String BASE_URL_BACK_POSTER = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        //binding
        TextView popularity = findViewById(R.id.tv_tvdetail_popularity);
        TextView freleaseDate = findViewById(R.id.tv_tvdetail_releasedata);
        TextView overview = findViewById(R.id.tv_tvdetail_sinopsis);
        ImageView poster = findViewById(R.id.img_tvdetail_poster);
        TextView judul = findViewById(R.id.tv_tvdetail_judul);
        TextView rating = findViewById(R.id.tv_tvdetail_rating);
        ImageView backPoster = findViewById(R.id.img_tvdetail_backposter);
        ProgressBar pbBackPoster = findViewById(R.id.progressBar_tvdetail_backposter);
        ProgressBar pbPoster = findViewById(R.id.progressBar_tvdetail_poster);

        //getting data from the objek that clicked in list
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        //binding view & data
        popularity.setText(String.valueOf(tvShow.getPopularity()));
        rating.setText(String.valueOf(tvShow.getVoteAverage()));
        freleaseDate.setText(tvShow.getFirstAirDate());
        overview.setText(tvShow.getOverview());
        judul.setText(tvShow.getOriginalName());

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
}
