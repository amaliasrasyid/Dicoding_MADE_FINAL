package amalia.dev.dicodingmade.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.Movie;


public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE ="extra movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //proses binding
        ImageView poster = findViewById(R.id.img_moviedetail_poster);
        TextView sinopsis = findViewById(R.id.tv_moviedetail_sinopsis);
        TextView judul = findViewById(R.id.tv_moviedetail_judul);
        TextView releaseDate = findViewById(R.id.tv_moviedetail_releasedate);
        TextView status = findViewById(R.id.tv_moviedetail_status);
        TextView rating = findViewById(R.id.tv_moviedetail_rating);


        //getting data that sended
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

//        //binding data dan view
//        sinopsis.setText(movie.getSinopsis());
//        judul.setText(movie.getJudul());
//        status.setText(movie.getStatus());
//        releaseDate.setText(movie.getTglRilis());
//        poster.setImageResource(movie.getPoster());
//        rating.setText(String.valueOf(movie.getRating()));

        //setting for navigation back
        ActionBar myActionBar = getSupportActionBar();
        if(myActionBar != null){
            myActionBar.setDisplayHomeAsUpEnabled(true);//akan meenable fungsi back-arrow ke activity parent (set parent di manifest.xml)
        }


    }
}