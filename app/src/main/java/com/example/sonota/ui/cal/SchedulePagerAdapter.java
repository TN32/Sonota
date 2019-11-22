package com.example.sonota.ui.cal;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Date;

public class SchedulePagerAdapter  extends FragmentPagerAdapter {
    private final int SCHEDULEPAGER_MAX_COUNT,SCHEDULEPAGER_DEFAULT_POSITION;
    private ScheduleListFragment mScheduleListFragment;
    private int beforePosition;
    private final Date DEFAULT_DATE;

    public SchedulePagerAdapter(FragmentManager fm,int schedulePagerMaxCount){
        super(fm);;
        this.SCHEDULEPAGER_MAX_COUNT = schedulePagerMaxCount;
        SCHEDULEPAGER_DEFAULT_POSITION = SCHEDULEPAGER_MAX_COUNT / 2;
        beforePosition = SCHEDULEPAGER_DEFAULT_POSITION;
        DEFAULT_DATE = new Date();
    }


    @Override
    public Fragment getItem(int position) {
        return new ScheduleListFragment(position - SCHEDULEPAGER_MAX_COUNT / 2);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object){
        if(mScheduleListFragment != object){
            mScheduleListFragment = (ScheduleListFragment)object;
        }
        super.setPrimaryItem(container,position,object);
    }

    public void setBeforePosition(int beforePosition){
        this.beforePosition = beforePosition;
    }

    public int getBeforePosition(){
        return beforePosition;
    }

    public Date getCurrentDate() {
        if(mScheduleListFragment == null)
            return null;
        return mScheduleListFragment.getDate();
    }

    public int getSCHEDULEPAGER_DEFAULT_POSITION(){ return SCHEDULEPAGER_DEFAULT_POSITION; };

    public Date getDEFAULT_DATE(){ return DEFAULT_DATE; }

    @Override
    public int getCount() {
        return SCHEDULEPAGER_MAX_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position - SCHEDULEPAGER_MAX_COUNT / 2);
    }
}
