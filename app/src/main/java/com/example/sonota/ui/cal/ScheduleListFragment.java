package com.example.sonota.ui.cal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SonotaDBOpenHelper helper;
    private SQLiteDatabase db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int index;
    private Calendar mCalendar;

    TextView tvDate;
    ScheduleListAdapter adapter;
    ParttimeJobListAdapter pAdapter;

    private OnFragmentInteractionListener mListener;

    public ScheduleListFragment() {
        this.index = 0;
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE,index);
    }

    public ScheduleListFragment(int position) {
        this.index = position;
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE,index);
    }

    public void setDayPosition(int position){
        this.index = position;
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE,index);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleListFragment newInstance(String param1, String param2) {
        ScheduleListFragment fragment = new ScheduleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public Date getDate(){
        return mCalendar.getTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_cal_schedule_list, container, false);

        tvDate =rootview.findViewById(R.id.tvDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.US);
        tvDate.setText(format.format(mCalendar.getTime()));

        if (helper == null){
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }

        String cDate = truncDate(getDate());
        String[] selectData = new  String[1];
        selectData[0] = cDate;

        //  引数distinctには、trueを指定すると検索結果から重複する行を削除します。
        //  引数tableには、テーブル名を指定します。
        //  引数columnsには、検索結果に含める列名を指定します。nullを指定すると全列の値が含まれます。
        //  引数selectionには、検索条件を指定します。
        //  引数selectionArgsには、検索条件のパラメータ（？で指定）に置き換わる値を指定します。
        //  引数groupByには、groupBy句を指定します。
        //  引数havingには、having句を指定します。
        //  引数orderByには、orderBy句を指定します。
        //  引数limitには、検索結果の上限レコードを数を指定します
        Cursor cursor = db.query(
                "t_schedule",
                new String[]{"schedule_code","schedule_name","schedule_stime","schedule_etime"},
                "schedule_day=?",
                selectData,
                null,
                null,
                "schedule_stime",
                null
        );

        cursor.moveToFirst();
        ArrayList<ScheduleListClass> listData = new ArrayList<ScheduleListClass>();

        for (int i = 0; i < cursor.getCount(); i++) {
            ScheduleListClass data = new ScheduleListClass(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            listData.add(data);
            cursor.moveToNext();
        }

       cursor = db.query(
                "t_shift",
               new String[]{"shift_code","shift_date","shift_stime","shift_etime","shift_btime"},
                null,
               null,
                null,
                null,
                null,
                null
       );

        cursor.moveToFirst();
        ArrayList<ParttimeJobListClass> plistData = new ArrayList<ParttimeJobListClass>();

        for (int i = 0; i < cursor.getCount(); i++) {
            ParttimeJobListClass data = new ParttimeJobListClass(cursor.getInt(0) ,"アルバイト先名",cursor.getString(1),cursor.getString(2),cursor.getInt(3));
            plistData.add(data);
            cursor.moveToNext();
        }

        cursor.close();

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new ScheduleListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_cal_schedule_cell // 自作したレイアウト
        );

        // idがlistのListViewを取得
        ListView elistView = (ListView) rootview.findViewById(R.id.list_view);
        elistView.setAdapter(adapter);

        pAdapter = new ParttimeJobListAdapter(
                getContext(),
                plistData, // 使用するデータ
                R.layout.list_cal_parttimejob_cell // 自作したレイアウト
        );

        ListView plistView = (ListView) rootview.findViewById(R.id.parttime_list_view);
        plistView.setAdapter(pAdapter);

        // Inflate the layout for this fragment
        return rootview;
    }

    public String truncDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd", Locale.US);
        return format.format(date);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            if(mCalendar != null && mListener != null){
                mListener.onScheduleLIstPageChanged(mCalendar.getTime());
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onScheduleLIstPageChanged(Date newDate);
    }
}
