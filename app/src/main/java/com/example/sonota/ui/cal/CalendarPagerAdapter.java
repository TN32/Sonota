package com.example.sonota.ui.cal;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Date;

public class CalendarPagerAdapter extends FragmentPagerAdapter {
    private final int CALENDARPAGER_MAX_COUNT;
    private CalendarFragment mCarenderFragment;

    public CalendarPagerAdapter(FragmentManager fm, int calendarPagerMaxCount) {
        super(fm);
        this.CALENDARPAGER_MAX_COUNT = calendarPagerMaxCount;
    }

    @Override
    public Fragment getItem(int position) {
        return new CalendarFragment(position - CALENDARPAGER_MAX_COUNT / 2);
    }

    //ページが切り替わったらアクセスするフラグメントを変える
    @Override
    public void setPrimaryItem(ViewGroup container,int position,Object object){
        if(mCarenderFragment != object){
            mCarenderFragment = (CalendarFragment)object;
        }
        super.setPrimaryItem(container,position,object);
    }


    //この辺から下は大体表示されてるフラグメントの関数を呼び出し元から呼べるようにする中間管理職
    public Date getCurrentDate() {
        if (mCarenderFragment == null)
            return null;
        return mCarenderFragment.getCurrentDate();
    }

    public void setCalendarCheckedClear(){
        mCarenderFragment.setCalendarCheckedClear();
    }

    public void setItemCheckedToDiff(int diff){
        mCarenderFragment.setItemCheckedToDiff(diff);
    }

    public void setItemCheckedDefaultPosition(int diff){
        mCarenderFragment.setItemCheckedDefaultPosition(diff);
    }

    public void setItemCheckedFirstDay(){
        mCarenderFragment.setItemCheckedFirstDay();
    }

    public void setItemCheckedLastView(){
        mCarenderFragment.setItemCheckedLastView();
    }

    @Override
    public int getCount() {
        return CALENDARPAGER_MAX_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position - CALENDARPAGER_MAX_COUNT / 2);
    }
}
