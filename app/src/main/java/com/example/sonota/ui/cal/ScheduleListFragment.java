package com.example.sonota.ui.cal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    int selectedPosition;
    ListView elistView;

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

        // idがlistのListViewを取得
        elistView = (ListView) rootview.findViewById(R.id.list_view);

        listload();

        elistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                selectedPosition = position;
                final String[] items = { "削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("Selector").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                             item_which pressed
                        switch (which) {
//                            case 0:
//                                UpdateExpenceFragment fragment = new UpdateExpenceFragment();
//                                Bundle bundle = new Bundle();
//
//                                bundle.putInt("selected", (int)adapter.getItemId(selectedPosition));
//                                bundle.putString("Memo",adapter.getCurrentMemo(selectedPosition));
//                                bundle.putInt("Amount", adapter.getAmount(selectedPosition));
//                                bundle.putString("addDate", adapter.getDate(selectedPosition));
//                                if(adapter.isCregitPayment(selectedPosition))
//                                    bundle.putString("isCredit", "true");
//                                else
//                                    bundle.putString("isCredit", "false");
//
//                                fragment.setArguments(bundle);
//                                // 変更画面を呼び出す
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                                transaction.replace(R.id.ec_mainsection, fragment);
//                                // 戻るボタンで戻ってこれるように
//                                transaction.addToBackStack("PaymentList");
//                                transaction.commit();
//                                break;
                            case 0:
                                adapter.getItemId(selectedPosition);
                                String[] whereId = new String[1];
                                whereId[0] = String.valueOf(adapter.getItemId(selectedPosition));
                                if(adapter.isPtj(position)){
                                    db.delete(
                                            "t_shift",
                                            "shift_code=?",
                                            whereId
                                    );
                                } else {
                                    db.delete(
                                            "t_schedule",
                                            "schedule_code=?",
                                            whereId
                                    );                                }
                                listload();
                                break;
                            case 1:
                                break;
                        }
                    }
                }).show();
            }
        });

        // Inflate the layout for this fragment
        return rootview;
    }

    public void listload() {
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
                "t_byteahead",
                new String[]{"byteahead_code","byteahead_name","byteahead_hwage"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        ArrayList<ParttimejobPlaceClass> pPlace = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++) {
            ParttimejobPlaceClass data = new ParttimejobPlaceClass(cursor.getInt(0),cursor.getString(1));
            pPlace.add(data);
            cursor.moveToNext();
        }

        cursor = db.query(
                "t_shift",
                new String[]{"shift_code","byteahead_code","shift_stime","shift_etime","shift_btime"},
                "shift_date=?",
                selectData,
                null,
                null,
                "shift_stime",
                null
        );

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            ScheduleListClass data = new ScheduleListClass(cursor.getInt(0) ,cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),pPlace);
            listData.add(data);
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


        elistView.setAdapter(adapter);
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
