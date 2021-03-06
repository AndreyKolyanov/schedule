package ru.kolyanov542255.schedule_classes;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Адольф on 22.01.2015.
 */
public class WeekPagerActivity extends FragmentActivity {

    private ViewPager viewPager;

    private ArrayList<ArrayList<DayOfWeek>> weeks;

    public static final String APP_PREFS = "APP_PREFS";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        weeks = DayLab.get(this).getWeeks();

        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                setTitle("Раписание ч/н");
                Log.d("PAGER", "App running");
                return DayListFragment.newInstance(i);
            }

            @Override
            public int getCount() {
                return weeks.size();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) { }

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }

            public void onPageSelected(int pos) {
                Resources res = getResources();
                String[] oddOrEven = res.getStringArray(R.array.WeekType);
                setTitle("Расписание " + oddOrEven[pos]);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.week_pager_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.pager_week_menu:
               Intent i = new Intent(this, SettingsActivity.class);
               startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
