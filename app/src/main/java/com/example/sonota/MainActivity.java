package com.example.sonota;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.sonota.ui.cal.CalFragment;
import com.example.sonota.ui.cal.CalFragmentControllAdapter;
import com.example.sonota.ui.cal.CalendarFragment;
import com.example.sonota.ui.cal.CalendarPagerAdapter;
import com.example.sonota.ui.cal.CustomViewPager;
import com.example.sonota.ui.cal.ScheduleListFragment;
import com.example.sonota.ui.cal.SchedulePagerAdapter;
import com.example.sonota.ui.clc.ClcFragment;
import com.example.sonota.ui.ec.EcFragment;
import com.example.sonota.ui.rc.RcFragment;
import com.example.sonota.ui.set.SetFragment;
import com.example.sonota.ui.tmp.TmpFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity
        implements FabControllInterface,CalendarFragment.OnFragmentInteractionListener, ScheduleListFragment.OnFragmentInteractionListener,
        CalFragment.OnFragmentInteractionListener, ClcFragment.OnFragmentInteractionListener, EcFragment.OnFragmentInteractionListener, RcFragment.OnFragmentInteractionListener,
        SetFragment.OnFragmentInteractionListener, TmpFragment.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;

    private CalFragmentControllAdapter mCalFragmentControllAdapter;
    private String CurrrentFragmentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_rc,
                R.id.nav_set,R.id.nav_ec,R.id.nav_cal,R.id.nav_clc,R.id.nav_tmp)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrrentFragmentID.equals("CalFrragment")) {
                }
                Context context = getApplicationContext();
                Toast.makeText(context , CurrrentFragmentID, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onFragmentInteraction(Uri uri){
    }

    public void onCalendarItemClick(String pickdate){
    }

    public void onCheckedDateChanged(Date afterDate){
        if(mCalFragmentControllAdapter != null){
            mCalFragmentControllAdapter.onCheckedDateChanged(afterDate);
        }
    }

    //表示されているカレンダーの外の日付が選択されたとき、カレンダーのページを移動する
    public void onCheckedNotCurrentMonth(boolean isNextMonth,boolean isOverCount){
        if(mCalFragmentControllAdapter != null){
            mCalFragmentControllAdapter.onCheckedNotCurrentMonth(isNextMonth,isOverCount);
        }
    }

    //スケジュールページャーをスワイプしたときカレンダーの選択を変更する
    public void onScheduleLIstPageChanged(Date newDate){
        if(mCalFragmentControllAdapter != null){
            mCalFragmentControllAdapter.onScheduleLIstPageChanged(newDate);
        }
    }

    public void onCreateCalFlagment(CalFragmentControllAdapter adapter){
        mCalFragmentControllAdapter = adapter;

    }

    public void setCurrrentFragmentID(String ID){
        this.CurrrentFragmentID = ID;
    }

}
