package com.example.sonota.ui.ec;

import android.content.Context;
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

import com.example.sonota.CustomFragment;
import com.example.sonota.MainActivity;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenceFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SonotaDBOpenHelper helper;
    private SQLiteDatabase db;

    TextView expenceTextviewThismonth;

    TextView expenceTextviewCashUsage;

    TextView expenceTextviewCreditPayment;
    ListView listView;
    ExpenceListAdapter adapter;


    public ExpenceFragment() {
        // Required empty public constructor
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
    public static ExpenceFragment newInstance(String param1, String param2) {
        ExpenceFragment fragment = new ExpenceFragment();
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
        View root = inflater.inflate(R.layout.fragment_ec_list_expence, container, false);

        if (helper == null){
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }

        listView = (ListView)root.findViewById(R.id.listview);

        expenceTextviewThismonth = (TextView) root.findViewById(R.id.PaymentListTextviewThisMonth);

        expenceTextviewCashUsage = (TextView) root.findViewById(R.id.PaymentTextViewCashUsage);

        expenceTextviewCreditPayment = (TextView) root.findViewById(R.id.PaymentTextviewCreditPayment);

        listload();

        // セルを選択されたら一覧画面フラグメント呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // 一覧画面へ値を渡す
                PaymentListFragment fragment = new PaymentListFragment();
                Bundle bundle = new Bundle();
                String dateText = adapter.getCurrentDateText(position);
                String[] splityear = dateText.split("年", -1);
                String[] splitmonth = splityear[1].split("月",-1);

                bundle.putString("selectedYear",splityear[0]);
                bundle.putString("selectedMonth",splitmonth[0]);
                fragment.setArguments(bundle);
                // 一覧画面を呼び出す
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.ec_mainsection, fragment);
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
                "t_payment",
                new String[]{"payment_code","payment_date","payment_money"},
                null,
                null,
                null,
                null,
                "payment_date desc"
        );


        cursor.moveToFirst();
        ArrayList<ExpenseListClass> listData = new ArrayList<ExpenseListClass>();
        int total = 0,cash = 0,credit = 0;
        String[] thisMonthSplit = splitDate(truncDate(new Date()));

        for (int i = 0; i < cursor.getCount();) {
            String[] monthSplit = splitDate(cursor.getString(1));
            String[] currentMonth = monthSplit;
            int currentMonthTotal = 0;
            do{
                if(monthSplit[0].equals(thisMonthSplit[0]) && monthSplit[1].equals(thisMonthSplit[1]))
                    total += cursor.getInt(2);
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

            ExpenseListClass data = new ExpenseListClass(i,monthSplit[0] + "年" + monthSplit[1] + "月",currentMonthTotal);
            listData.add(data);
        }

        expenceTextviewThismonth.setText(String.valueOf(total));
        expenceTextviewCashUsage.setText(String.valueOf(cash));
        expenceTextviewCreditPayment.setText(String.valueOf(credit));

        cursor.close();

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new ExpenceListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_ec_expence_cell // 自作したレイアウト
        );

        listView.setAdapter(adapter);
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
}
