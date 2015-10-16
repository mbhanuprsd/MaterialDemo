package com.xcubelabs.bhanuprasadm.materialdemo.activity;

import android.content.ComponentName;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.xcubelabs.bhanuprasadm.materialdemo.R;
import com.xcubelabs.bhanuprasadm.materialdemo.extras.Constants;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.BoxOfficeFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.NavigationDrawerFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.SearchFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.fragment.UpcomingFragment;
import com.xcubelabs.bhanuprasadm.materialdemo.interfaces.SortListener;
import com.xcubelabs.bhanuprasadm.materialdemo.services.MyService;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

public class MainActivity extends AppCompatActivity implements MaterialTabListener, View.OnClickListener, Constants {

    public static final int MOVIES_SEARCH = 0;
    public static final int MOVIES_UPCOMING = 1;
    public static final int MOVIES_BOXOFFICE = 2;
    public static final String TAG_NAME = "sort_name";
    public static final String TAG_DATE = "sort_date";
    public static final String TAG_RATING = "sort_rating";
    private static final int JOB_ID = 123;

    private MaterialTabHost materialTabHost;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private JobScheduler mJobScheduler;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobScheduler = JobScheduler.getInstance(this);
        constructJobInfo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        materialTabHost = (MaterialTabHost) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
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

        ImageView sortIcon = new ImageView(this);
        sortIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort));
        FloatingActionButton sortActionButton = new FloatingActionButton.Builder(this)
                .setContentView(sortIcon)
                .setBackgroundDrawable(R.drawable.floating_action_button_bg)
                .build();

        SubActionButton.Builder subActionBuilder = new SubActionButton.Builder(this)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.sub_action_bg));

        ImageView sortNameIcon = new ImageView(this);
        sortNameIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_name));
        ImageView sortDateIcon = new ImageView(this);
        sortDateIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_date));
        ImageView sortRatingIcon = new ImageView(this);
        sortRatingIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_rating));

        SubActionButton sortNameButton = subActionBuilder.setContentView(sortNameIcon).build();
        SubActionButton sortDateButton = subActionBuilder.setContentView(sortDateIcon).build();
        SubActionButton sortRatingButton = subActionBuilder.setContentView(sortRatingIcon).build();

        sortNameButton.setTag(TAG_NAME);
        sortDateButton.setTag(TAG_DATE);
        sortRatingButton.setTag(TAG_RATING);

        sortNameButton.setOnClickListener(this);
        sortDateButton.setOnClickListener(this);
        sortRatingButton.setOnClickListener(this);

        FloatingActionMenu floatingActionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(sortNameButton)
                .addSubActionView(sortDateButton)
                .addSubActionView(sortRatingButton)
                .attachTo(sortActionButton)
                .build();
    }

    private void constructJobInfo() {
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        builder.setPeriodic(MINUTE)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
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

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof SortListener) {
            if (v.getTag().equals(TAG_NAME)) {
                ((SortListener) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_DATE)) {
                ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_RATING)) {
                ((SortListener) fragment).onSortByRating();
            }
        }
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        int[] tabImages = {R.drawable.ic_sort_name, R.drawable.ic_sort_date, R.drawable.ic_sort_rating};

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
