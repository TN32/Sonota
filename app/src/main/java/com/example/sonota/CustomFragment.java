package com.example.sonota;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomFragment extends Fragment {

    protected FabControllInterface mFabControllInterface;
    protected int fabCount = 0;
    protected boolean isSetToCurrentFragment = true;

    protected SonotaDBOpenHelper helper;
    protected SQLiteDatabase db;

    protected ListView listView;
    protected int selectedPosition;

    protected boolean isNewItem;
    protected String pearentId = "";

    public String truncDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd", Locale.US);
        return format.format(date);
    }

    public String[] splitDate(String dateString){
        return dateString.split("_");
    }

    public String sharpingDate(String dateString){
        String[] dateText = splitDate(dateString);
        return dateText[0] + "年" + dateText[1] + "月" + dateText[2] + "日";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFabControllInterface = (FabControllInterface)context;
    }

    @Override
    public void onResume() {
        if(isSetToCurrentFragment){
            mFabControllInterface.setCurrrentFragment(this);
            mFabControllInterface.setFabCount(fabCount);
        }
        super.onResume();
    }

    //表示されたときイベント
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (mFabControllInterface != null) {
                mFabControllInterface.setCurrrentFragment(this);
                mFabControllInterface.setFabCount(fabCount);
            }
        }
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onFab1Clicked(int fabId){
        Toast.makeText(getContext() , "このFragmentのFAB" + fabId + "は未実装です。", Toast.LENGTH_LONG).show();
    }

    public boolean onBackPressed(){
        return false;
    }

}
