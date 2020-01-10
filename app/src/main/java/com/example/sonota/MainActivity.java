package com.example.sonota;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.example.sonota.ui.cal.CalenderContentFragmentControllAdapter;
import com.example.sonota.ui.cal.CalendarFragment;
import com.example.sonota.ui.cal.CalenderContentFragment;
import com.example.sonota.ui.cal.CalenderYearShiftFragment;
import com.example.sonota.ui.cal.CustomViewPager;
import com.example.sonota.ui.cal.ScheduleListFragment;
import com.example.sonota.ui.clc.ClcFragment;
import com.example.sonota.ui.ec.EcFragment;
import com.example.sonota.ui.rc.RcFragment;
import com.example.sonota.ui.set.SetFragment;
import com.example.sonota.ui.tmp.TmpFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity
        implements FabControllInterface,CalendarFragment.OnFragmentInteractionListener, ScheduleListFragment.OnFragmentInteractionListener,
        CalenderContentFragment.OnFragmentInteractionListener, ClcFragment.OnFragmentInteractionListener, EcFragment.OnFragmentInteractionListener, RcFragment.OnFragmentInteractionListener,
        SetFragment.OnFragmentInteractionListener, TmpFragment.OnFragmentInteractionListener, CalenderYearShiftFragment.OnFragmentInteractionListener, CustomViewPager.OnViewInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;

    private CalenderContentFragmentControllAdapter mCalFragmentControllAdapter;
    CustomFragment currentFragment;

    FloatingActionButton fab,fab1,fab2,fab3;
    TextView text1,text2,text3;

    GridView gridView;
    private int savedCalenderPosition;

    Animation sclup,scldown,fabopen1,fabopen2,fabopen3,fabclose,fabclose1,fabclose2,fabclose3;
    View view_dark;
    private float originalWeight;
    boolean isOpen=false;

    private int fabCount = 0;

    private SonotaDBOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab_parent);
        fab1 = findViewById(R.id.fab_child_top);
        fab2 = findViewById(R.id.fab_child_middle);
        fab3 = findViewById(R.id.fab_child_bottom);

        text1 = findViewById(R.id.fabtext_top);
        text2 = findViewById(R.id.fabtext_middle);
        text3 = findViewById(R.id.fabtext_bottom);

        gridView = (GridView)findViewById(R.id.calendargridview);

        view_dark = findViewById(R.id.view_dark);
        //アニメーションセット
        sclup = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scldown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        fabopen1 = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabopen2 = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabopen3 = AnimationUtils.loadAnimation(this,R.anim.fab_open);

        fabclose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        fabclose1 = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        fabclose2 = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        fabclose3= AnimationUtils.loadAnimation(this,R.anim.fab_close);



// テキストを設定して表示
        text1.setText("ルビィちゃんA");
        text2.setText("ルビィちゃんB");
        text3.setText("ルビィちゃんC");

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

        FloatingActionButton fab = findViewById(R.id.fab_parent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //アニメーション
                if (currentFragment.fabCount >= 2) {
                    animateFab();
                }else {
                    //一つの時の処理
                    currentFragment.onFab1Clicked(0);
                }
            }
        });

        //暗転view
        view_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment.onFab1Clicked(1);
                animateFab();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment.onFab1Clicked(2);
                animateFab();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment.onFab1Clicked(3);
                animateFab();
            }
        });
    }

    //アニメソッド（FAB）
    private void animateFab(){
        if (isOpen)
        {
            fab.startAnimation(sclup);
            if (currentFragment.fabCount > 0){
                fab1.startAnimation(fabclose1);
                text1.startAnimation(fabclose1);
                fab1.setClickable(false);
                if(currentFragment.fabCount > 1){
                    fab2.startAnimation(fabclose2);
                    text2.startAnimation(fabclose2);
                    fab2.setClickable(false);
                    if(currentFragment.fabCount > 2){
                        fab3.startAnimation(fabclose3);
                        text3.startAnimation(fabclose3);
                        fab3.setClickable(false);
                    }
                }
            }
            view_dark.setVisibility(View.GONE);
            isOpen=false;
        }
        else
        {
            fab.startAnimation(scldown);
            if (currentFragment.fabCount > 0){
                fab1.startAnimation(fabopen1);
                text1.startAnimation(fabopen1);
                fab1.setClickable(true);
                if(currentFragment.fabCount > 1){
                    fab2.startAnimation(fabopen2);
                    text2.startAnimation(fabopen2);
                    fab2.setClickable(true);
                    if(currentFragment.fabCount > 2){
                        fab3.startAnimation(fabopen3);
                        text3.startAnimation(fabopen3);
                        fab3.setClickable(true);
                    }
                }
            }
            view_dark.setVisibility(View.VISIBLE);
            isOpen=true;
        }

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

    public void OnCalledYearShift(){
        if(mCalFragmentControllAdapter != null){
//            CalenderYearShiftFragment fragment = new CalenderYearShiftFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.nav_view,fragment);
//            transaction.commit();
        }
    }

    //スケジュールページャーをスワイプしたときカレンダーの選択を変更する
    public void onScheduleLIstPageChanged(Date newDate){
        if(mCalFragmentControllAdapter != null){
            mCalFragmentControllAdapter.onScheduleLIstPageChanged(newDate);
        }
    }

    public void onCreateCalFlagment(CalenderContentFragmentControllAdapter adapter){
        mCalFragmentControllAdapter = adapter;
    }

    public void setCurrrentFragment(CustomFragment fragment){
        if(isOpen){
            animateFab();
        }

        currentFragment = fragment;

        //FABが1以下の時の画像変更
        if (currentFragment.fabCount == 1){
            fab.setImageResource(R.mipmap.ic_launcher_cledit_foreground);
        }else {
            fab.setImageResource(android.R.drawable.ic_input_add);
        }
        view_dark.setVisibility(View.GONE);
        isOpen=false;
    }

    public void setFabCount(int count){
        fabCount = count;
        if(fabCount > 0){
            fab.startAnimation(fabopen1);
            fab.setClickable(true);
        }
        else if(fab.isClickable()){
            fab.startAnimation(fabclose);
            fab.setClickable(false);
        }
    }

    public void saveCurrentPosition(int position){
        savedCalenderPosition = position;
    }

    public int getSavedCalenderPosition(){
        int position = savedCalenderPosition;
        if(position != -1){
            clearCurrentPosition();
        }
        return position;
    }

    public void clearCurrentPosition(){
        savedCalenderPosition = -1;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // ExpandするViewの元のサイズを保持する
        if (currentFragment instanceof CalenderContentFragment){
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cal_CalenderSection);
            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            originalWeight = ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).weight;
        }
    }
    public class ResizeAnimation extends Animation {
        final float addWeight;
        View view;
        android.text.Layout Layout;
        float startWeight;

        public ResizeAnimation(View view, float addWeight, float startWeight) {
            this.view = view;
            this.addWeight = addWeight;
            this.startWeight = startWeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newWeight = (int) (startWeight + addWeight * interpolatedTime);

            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.cal_CalenderSection);
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            params.weight=newWeight;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public void animeup(){
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.cal_CalenderSection);
        final LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) linearLayout.getLayoutParams();

        // ビューを開くアニメーションを生成
        final ResizeAnimation expandAnimation = new ResizeAnimation(linearLayout, originalWeight, 0);
        expandAnimation.setDuration(200);
        linearLayout.clearAnimation();
        if(params.weight <99)
            linearLayout.startAnimation(expandAnimation);

        //Toast.makeText(this , "上", Toast.LENGTH_LONG).show();
    }

    public void animedown(){
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.cal_CalenderSection);
        final LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) linearLayout.getLayoutParams();

        // ビューを閉じるアニメーションを生成
        final ResizeAnimation collapseAnimation = new ResizeAnimation(linearLayout, -params.weight, params.weight);
        collapseAnimation.setDuration(200);
        // 下フリック
        linearLayout.clearAnimation();
        if(params.weight >99)
            linearLayout.startAnimation(collapseAnimation);
        // Toast.makeText(this, "下", Toast.LENGTH_LONG).show();
    }
}
