package amalia.dev.consumerfavorites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import amalia.dev.consumerfavorites.adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MUST USING getChilFragmentManager cause without it, when onResume() after onDestroy() views dissappear
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(this,getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager_favorites);
        viewPager.setAdapter(tabPagerAdapter);


        TabLayout tabLayout = findViewById(R.id.tablayout_favorites);
        tabLayout.setupWithViewPager(viewPager);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Consumer Favorites");
        }
    }
}
