package com.example.sonota.ui.ec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;
import java.util.Date;


public class PaymentListFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listView;
    PaymentListAdapter adapter;
    String year,month;
    TextView paymentListTextviewThismonth;
    TextView paymentTextviewCashUsage;
    TextView paymentTextviewCreditPayment;

    int selectedPosition;

    public PaymentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceiveListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentListFragment newInstance(String param1, String param2) {
        PaymentListFragment fragment = new PaymentListFragment();
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
        View root = inflater.inflate(R.layout.fragment_ec_list_payment, container, false);
        Bundle args = getArguments();



        paymentListTextviewThismonth = (TextView)root.findViewById(R.id.PaymentListTextviewThisMonth);;
        paymentTextviewCashUsage = (TextView)root.findViewById(R.id.PaymentTextViewCashUsage);
        paymentTextviewCreditPayment = (TextView)root.findViewById(R.id.PaymentTextviewCreditPayment);



        this.year = args.getString("selectedYear");
        this.month = args.getString("selectedMonth");

        if(Integer.valueOf(month) < 10){
            month = "0" + Integer.valueOf(month);
        }

        String selected = year + "年" + month + "月";

        TextView textView = (TextView)root.findViewById(R.id.Exl_Tx_Month);
        textView.setText(selected);


        Button prevbButton = (Button) root.findViewById(R.id.Exl_B_LastMonth);

        prevbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一覧画面へ値を渡す
                PaymentListFragment fragment = new PaymentListFragment();
                Bundle bundle = new Bundle();


                int monthInt = Integer.parseInt(month) - 1;
                int yearInt = Integer.parseInt(year);

                if(monthInt < 1){
                    monthInt = 12;
                    yearInt--;
                }

                bundle.putString("selectedYear",String.valueOf(yearInt));
                bundle.putString("selectedMonth",String.valueOf(monthInt));
                fragment.setArguments(bundle);
                // 一覧画面を呼び出す
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.ec_mainsection, fragment);
                // 戻るボタンで戻ってこれるよに
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        Button nextbButton = (Button) root.findViewById(R.id.Exl_B_NextMonth);

        nextbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一覧画面へ値を渡す
                PaymentListFragment fragment = new PaymentListFragment();
                Bundle bundle = new Bundle();

                int monthInt = Integer.parseInt(month) + 1;
                int yearInt = Integer.parseInt(year);

                if(monthInt > 12){
                    monthInt = 1;
                    yearInt++;
                }

                bundle.putString("selectedYear",String.valueOf(yearInt));
                bundle.putString("selectedMonth",String.valueOf(monthInt));
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

        // idがlistのListViewを取得
        listView = (ListView) root.findViewById(R.id.listview);

        listload();

        // ここにダイアログイベントを発生
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                final String[] items = {"変更", "削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("Selector").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                             item_which pressed
                        switch (which) {
                            case 0:
                                UpdateExpenceFragment fragment = new UpdateExpenceFragment();
                                Bundle bundle = new Bundle();

                                bundle.putInt("selected", (int)adapter.getItemId(selectedPosition));
                                bundle.putString("Memo",adapter.getCurrentMemo(selectedPosition));
                                bundle.putInt("Amount", adapter.getAmount(selectedPosition));
                                bundle.putString("addDate", adapter.getDate(selectedPosition));
                                if(adapter.isCregitPayment(selectedPosition))
                                    bundle.putString("isCredit", "true");
                                else
                                    bundle.putString("isCredit", "false");

                                fragment.setArguments(bundle);
                                // 変更画面を呼び出す
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.ec_mainsection, fragment);
                                // 戻るボタンで戻ってこれるように
                                transaction.addToBackStack("PaymentList");
                                transaction.commit();
                                break;
                            case 1:
                                adapter.getItemId(selectedPosition);
                                String[] whereId = new String[1];
                                whereId[0] = String.valueOf(adapter.getItemId(selectedPosition));
                                db.delete(
                                        "t_payment",
                                        "payment_code=?",
                                        whereId
                                );
                                listload();
                                break;
                            case 2:
                                break;
                        }
                    }
                }).show();
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

        String[] selectArgs = {year + "_" + month + "%"};

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
                new String[]{"payment_code","payment_date","payment_memo","payment_money","payment_cpay"},
                "payment_date like ?",
                selectArgs,
                null,
                null,
                "payment_date desc"
        );


        cursor.moveToFirst();
        ArrayList<PaymentClass> listData = new ArrayList<PaymentClass>();
        int total = 0,cash = 0,credit = 0;

        for (int i = 0; i < cursor.getCount(); i++) {
            PaymentClass data = new PaymentClass(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4));
            listData.add(data);
            total += data.getAmount();
            if(data.isCregitPayment()){
                credit += data.getAmount();
            }else {
                cash += data.getAmount();
            }
            cursor.moveToNext();
        }

        paymentListTextviewThismonth.setText(String.valueOf(total));
        paymentTextviewCashUsage.setText(String.valueOf(cash));
        paymentTextviewCreditPayment.setText(String.valueOf(credit));

        cursor.close();

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new PaymentListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_ec_payment_cell // 自作したレイアウト
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
