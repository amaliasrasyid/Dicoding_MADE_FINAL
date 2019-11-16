package amalia.dev.dicodingmade.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import amalia.dev.dicodingmade.R;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW ="extra movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
    }
}
