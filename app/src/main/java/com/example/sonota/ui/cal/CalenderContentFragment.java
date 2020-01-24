package com.example.sonota.ui.cal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sonota.CustomFragment;
import com.example.sonota.FabControllInterface;
import com.example.sonota.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class CalenderContentFragment extends CustomFragment {
    private final int INTEGER_NULL = -100000;
    private final int CALENDARPAGER_MAX_COUNT = 1000;
    private final int SCHEDULEPAGER_MAX_COUNT = 30000;
    private CustomViewPager calendarPager,schedulePager;
    private CalendarPagerAdapter mCalendarPagerAdapter;
    private SchedulePagerAdapter mSchedulePagerAdapter;
    private Button btAddExpence;
    private Date beforeDate = new Date();

    private OnFragmentInteractionListener mListener;
    private FabControllInterface mFabControllInterface;

    public CalenderContentFragment(){
        fabCount = 4;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cal_calender_content, container, false);

        calendarPager = root.findViewById(R.id.CalendarPager);
        mCalendarPagerAdapter = new CalendarPagerAdapter(getChildFragmentManager(),CALENDARPAGER_MAX_COUNT);

        calendarPager.setAdapter(mCalendarPagerAdapter);
        calendarPager.setCurrentItem(CALENDARPAGER_MAX_COUNT / 2);

        schedulePager = root.findViewById(R.id.SchedulePager);
        mSchedulePagerAdapter = new SchedulePagerAdapter(getChildFragmentManager(),SCHEDULEPAGER_MAX_COUNT);

        schedulePager.setAdapter(mSchedulePagerAdapter);
        schedulePager.setCurrentItem(SCHEDULEPAGER_MAX_COUNT / 2);

        setListtener(root);

        return root;
    }

    public void setListtener(View root){


        calendarPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //カレンダーをページ移動した時、移動前のカレンダーの日付選択をクリア
                mCalendarPagerAdapter.setCalendarCheckedClear();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_IDLE == state) {
                    schedulePager.setPagingEnabled(true);
                }else if (ViewPager.SCROLL_STATE_DRAGGING == state){
                    schedulePager.setPagingEnabled(false);
                }
            }
        });

        schedulePager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_IDLE == state) {
                    calendarPager.setPagingEnabled(true);
                }else if (ViewPager.SCROLL_STATE_DRAGGING == state){
                    calendarPager.setPagingEnabled(false);
                }
            }
        });
    }

    public void onFragmentInteraction(Uri uri){
    }

    public void onCalendarItemClick(String pickdate){
    }

    public void onCheckedDateChanged(Date afterDate){
        if(mCalendarPagerAdapter == null || mSchedulePagerAdapter == null)
            return;

        if(dateTruncate(mCalendarPagerAdapter.getCurrentDate()) .equals(dateTruncate(mSchedulePagerAdapter.getCurrentDate())))
            return;

        schedulePager.setCurrentItem(mSchedulePagerAdapter.getSCHEDULEPAGER_DEFAULT_POSITION() + dayDiff(afterDate,mSchedulePagerAdapter.getDEFAULT_DATE()));
        beforeDate = afterDate;
        mSchedulePagerAdapter.setBeforePosition(schedulePager.getCurrentItem());
    }

    //表示されているカレンダーの外の日付が選択されたとき、カレンダーのページを移動する
    public void onCheckedNotCurrentMonth(boolean isNextMonth,boolean isOverCount){
        SimpleDateFormat format = new SimpleDateFormat("d", Locale.US);
        String dateString = format.format(mCalendarPagerAdapter.getCurrentDate());

        if (isNextMonth == true)
            calendarPager.setCurrentItem(calendarPager.getCurrentItem() + 1);
        else
            calendarPager.setCurrentItem(calendarPager.getCurrentItem() - 1);

        if(isOverCount == true){
            if (isNextMonth == true){
                mCalendarPagerAdapter.setItemCheckedPosition("1");
            }
            else {
                mCalendarPagerAdapter.setItemCheckedLastView();
            }
        }else {
            mCalendarPagerAdapter.setItemCheckedPosition(dateString);
        }
    }

    //日付の差分を取得
    public int dayDiff(Date afterDate,Date beforeDate){
        if(afterDate == null || beforeDate == null)
            return INTEGER_NULL;

        long afterTime = dateTruncate(afterDate).getTime();
        long beforeTime = dateTruncate(beforeDate).getTime();
        long daydiff = (afterTime - beforeTime) / (1000 * 60 * 60 * 24);

        return (int)daydiff;
    }

    //比較するためにDate型の時分秒を切り捨てて年月日だけにする
    static Date dateTruncate(Date date){
        if(date == null)
            return null;
        Calendar dateTime = Calendar.getInstance();
        dateTime.setTime(date);
        Calendar truncDate = Calendar.getInstance();
        truncDate.clear();
        truncDate.set(dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH));
        return truncDate.getTime();
    }

    //スケジュールページャーをスワイプしたと気カレンダーに選択を変更する
    public void onScheduleLIstPageChanged(Date newDate){
        if (mCalendarPagerAdapter == null)
            return;

        int diff = dayDiff(newDate,mCalendarPagerAdapter.getCurrentDate());

        if(diff == 0 || diff == INTEGER_NULL)
            return;

        if(mSchedulePagerAdapter.getBeforePosition() > schedulePager.getCurrentItem())
            mCalendarPagerAdapter.setItemCheckedToDiff(diff);
        else{
            mCalendarPagerAdapter.setItemCheckedToDiff(diff);
        }
        mSchedulePagerAdapter.setBeforePosition(schedulePager.getCurrentItem());
    }

    @Override
    public void onFab1Clicked(int fabId){
        CustomFragment fragment;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        String addDate = truncDate(dateTruncate(mCalendarPagerAdapter.getCurrentDate()));
        bundle.putString("addDate",addDate);
        mListener.saveCurrentPosition(mCalendarPagerAdapter.getCheckedItemPosition());

        switch (fabId){
            case 1:
                fragment = new AddExpenceFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.cal_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("CalenderContent");
                transaction.commit();
                return;
            case 2:
                fragment = new AddIncomeFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.cal_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("CalenderContent");
                transaction.commit();
                return;
            case 3:
                fragment = new AddParttimejobFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.cal_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("CalenderContent");
                transaction.commit();
                return;
            case 4:
                fragment = new AddEventFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.cal_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("CalenderContent");
                transaction.commit();
                return;
        }
        super.onFab1Clicked(fabId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFabControllInterface = (FabControllInterface)context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }

        CalenderContentFragmentControllAdapter adapter = new CalenderContentFragmentControllAdapter(this);
        mListener.onCreateCalFlagment(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onCreateCalFlagment(CalenderContentFragmentControllAdapter adapter);
        void saveCurrentPosition(int position);
    }
}
