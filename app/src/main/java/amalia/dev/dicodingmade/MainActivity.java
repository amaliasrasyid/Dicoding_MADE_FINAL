package amalia.dev.dicodingmade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MovieFragment fragment = new MovieFragment();
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.constraintLayout_main_container,fragment);
//        transaction.commit();

        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(this,getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager_main);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout_main);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setElevation(0);




    }
}
