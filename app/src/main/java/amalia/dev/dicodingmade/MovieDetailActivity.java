package amalia.dev.dicodingmade;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE ="extra movie";

    ImageView poster;
    TextView sinopsis,judul,status,releaseDate,rating;
    TextView namaPemeran1,jobPemeran1,namaPemeran2,jobPemeran2,namaPemeran3,jobPemeran3,namaPemeran4,jobPemeran4,namaPemeran5,jobPemeran5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //proses binding
        poster = findViewById(R.id.img_moviedetail_poster);
        sinopsis = findViewById(R.id.tv_moviedetail_sinopsis);
        judul = findViewById(R.id.tv_moviedetail_judul);
        releaseDate = findViewById(R.id.tv_moviedetail_releasedate);
        status = findViewById(R.id.tv_moviedetail_status);
        rating = findViewById(R.id.tv_moviedetail_rating);

//        namaPemeran1 = findViewById(R.id.tv_moviedetail_pemeran1_name);
//        jobPemeran1 = findViewById(R.id.tv_moviedetail_pemeran1_job);
//        namaPemeran2 = findViewById(R.id.tv_moviedetail_pemeran2_name);
//        jobPemeran2= findViewById(R.id.tv_moviedetail_pemeran2_job);
//        namaPemeran3 = findViewById(R.id.tv_moviedetail_pemeran3_name);
//        jobPemeran3= findViewById(R.id.tv_moviedetail_pemeran3_job);
//        namaPemeran4 = findViewById(R.id.tv_moviedetail_pemeran4_name);
//        jobPemeran4= findViewById(R.id.tv_moviedetail_pemeran4_job);
//        namaPemeran5 = findViewById(R.id.tv_moviedetail_pemeran5_name);
//        jobPemeran5 = findViewById(R.id.tv_moviedetail_pemeran5_job);


        //getting data that sended
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        //binding data dan view
        poster.setImageResource(movie.getPoster());
        sinopsis.setText(movie.getSinopsis());
        judul.setText(movie.getJudul());
        status.setText(movie.getStatus());
        releaseDate.setText(movie.getTglRilis());
        rating.setText(String.valueOf(movie.getRating()));
        rating.setPaintFlags(rating.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);



    }
}
