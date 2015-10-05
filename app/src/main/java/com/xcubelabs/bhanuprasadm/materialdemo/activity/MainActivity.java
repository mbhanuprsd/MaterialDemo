package com.xcubelabs.bhanuprasadm.materialdemo.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xcubelabs.bhanuprasadm.materialdemo.R;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.BoxOfficeFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.NavigationDrawerFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.SearchFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.UpcomingFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    public static final int MOVIES_SEARCH = 0;
    public static final int MOVIES_UPCOMING = 1;
    public static final int MOVIES_BOXOFFICE = 2;
    private MaterialTabHost materialTabHost;
    private ViewPager viewPager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        materialTabHost = (MaterialTabHost) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //noinspection deprecation
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                materialTabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            materialTabHost.addTab(
                    materialTabHost.newTab()
                            .setIcon(adapter.getIcon(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem1:
                Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.navigate:
                startActivity(new Intent(MainActivity.this, SubActivity.class));
                break;

            case R.id.menuItem_tabLibrary:
                startActivity(new Intent(MainActivity.this, TabLibraryActivity.class));
                break;

            case R.id.menu_vector:
                startActivity(new Intent(MainActivity.this, VectorTestActivity.class));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        int[] tabImages = {R.drawable.ic_images, R.drawable.ic_videos, R.drawable.ic_files};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Drawable getIcon(int position) {
            return getResources().getDrawable(tabImages[position]);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case MOVIES_SEARCH:
                    fragment = SearchFragment.newInstance("", "");
                    break;

                case MOVIES_UPCOMING:
                    fragment = UpcomingFragment.newInstance("", "");
                    break;

                case MOVIES_BOXOFFICE:
                    fragment = BoxOfficeFragment.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabImages.length;
        }
    }
}
