package amalia.dev.dicodingmade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieFragment fragment = new MovieFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.constraintLayout_main_container,fragment);
        transaction.commit();


    }
}
