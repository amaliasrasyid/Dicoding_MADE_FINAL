package amalia.dev.dicodingmade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView rv = findViewById(R.id.rv_main);
        MovieAdapter adapter = new MovieAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        //set adapter ke recyclerview
        rv.setAdapter(adapter);
    }
}
