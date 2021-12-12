package josemanuel.marin.sendasur.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.controller.PagerAdapter;


public class MainActivity extends AppCompatActivity {

    private static boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        final ViewPager2 viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        if(findViewById(R.id.senda_container)!=null){
            mTwoPane = true;
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static boolean getTwoPane(){
        return mTwoPane;
    }
}