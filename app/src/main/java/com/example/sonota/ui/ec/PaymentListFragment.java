package com.example.sonota.ui.ec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;


public class PaymentListFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String year,month;

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

        TextView paymentListTextviewThismonth = (TextView)root.findViewById(R.id.PaymentListTextviewThisMonth);
        //         当月の出費を表示
        paymentListTextviewThismonth.setText("30000");

        TextView paymentTextviewCashUsage = (TextView)root.findViewById(R.id.PaymentTextViewCashUsage);
        // 当月の現金の出費を表示
        paymentTextviewCashUsage.setText("20000");

        TextView paymentTextviewCreditPayment = (TextView)root.findViewById(R.id.PaymentTextviewCreditPayment);
        //　当月のクレジットの出費を表示
        paymentTextviewCreditPayment.setText("15000");



        this.year = args.getString("selectedYear");
        this.month = args.getString("selectedMonth");

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

        ArrayList<PaymentClass> listData = new ArrayList<>();
        for(int i = 1; i <=  12; i++) {
            PaymentClass data = new PaymentClass(i, i + "日", "コンビニ", 30000000 + i);
            listData.add(data);
        }

        final PaymentListAdapter arrayAdapter = new PaymentListAdapter(getContext(),listData,R.layout.list_ec_payment_cell);

        // idがlistのListViewを取得
        ListView listView = (ListView) root.findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);



        // ここにダイアログイベントを発生
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String[] items = {"変更", "削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("Selector").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                             item_which pressed

                        switch (which) {
                            case 0:
                                UpdateExpenceFragment fragment = new UpdateExpenceFragment();
                                Bundle bundle = new Bundle();

                                fragment.setArguments(bundle);
                                // 変更画面を呼び出す
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.ec_mainsection, fragment);
                                // 戻るボタンで戻ってこれるように
                                transaction.addToBackStack(null);
                                transaction.commit();
                            case 1:
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
