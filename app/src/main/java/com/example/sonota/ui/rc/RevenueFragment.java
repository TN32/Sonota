package com.example.sonota.ui.rc;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RevenueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters

    RevenueListAdapter arrayAdapter;
    TextView tv_rc_revenue;
    
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RevenueFragment() {
        // Required empty public constructor
        fabCount = 0;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Expence.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueFragment newInstance(String param1, String param2) {
        RevenueFragment fragment = new RevenueFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rc_list_revenue, container, false);

        tv_rc_revenue = (TextView)root.findViewById(R.id.tv_rc_revenue);

        if (helper == null){
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }
        listView = (ListView)root.findViewById(R.id.listview);

//        expenceTextviewThismonth = (TextView) root.findViewById(R.id.PaymentListTextviewThisMonth);

        ListView listView = (ListView) root.findViewById(R.id.listview);

        listload();

        // セルを選択されたら一覧画面フラグメント呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // 一覧画面へ値を渡す
                ReceiveListFragment fragment = new ReceiveListFragment();
                Bundle bundle = new Bundle();
                String dateText = arrayAdapter.getCurrentDateText(position);
                String[] splityear = dateText.split("年", -1);
                String[] splitmonth = splityear[1].split("月",-1);

                bundle.putString("selectedYear",splityear[0]);
                bundle.putString("selectedMonth",splitmonth[0]);
                fragment.setArguments(bundle);
                // 一覧画面を呼び出す
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.rc_mainsection, fragment);
                // 戻るボタンで戻ってこれるように
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return root;
    }

    public void listload(){
        if (helper == null){
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }

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
                "t_income",
                new String[]{"income_code","income_day","income_memo"},
                null,
                null,
                null,
                null,
                "income_day desc"
        );


        cursor.moveToFirst();
        ArrayList<RevenueListClass> listData = new ArrayList<RevenueListClass>();
        int total = 0,cash = 0,credit = 0;
        String[] thisMonthSplit = splitDate(truncDate(new Date()));

        for (int i = 0; i < cursor.getCount();) {
            String[] monthSplit = splitDate(cursor.getString(1));
            String[] currentMonth = monthSplit;
            int currentMonthTotal = 0;
            do{
                currentMonthTotal += cursor.getInt(2);
                String s = cursor.getString(1);
                cursor.moveToNext();
                i++;
                if(i < cursor.getCount()){
                    currentMonth = splitDate(cursor.getString(1));
                }
                else {
                    break;
                }
            }while (monthSplit[0].equals(currentMonth[0]) && monthSplit[1].equals(currentMonth[1]));

            RevenueListClass data = new RevenueListClass(i,monthSplit[0] + "年" + monthSplit[1] + "月",currentMonthTotal);
            listData.add(data);
        }

        tv_rc_revenue.setText(String.valueOf(total));

        cursor.close();

        /**
         * CustomarrayAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        arrayAdapter = new RevenueListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_rc_revenue_cell // 自作したレイアウト
        );

        listView.setAdapter(arrayAdapter);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
    }

    @Override
    public void onFab1Clicked(int fabId){
        Toast.makeText(getContext() , "このFragmentのFAB" + fabId + "は未実装です。", Toast.LENGTH_LONG).show();
    }
}
