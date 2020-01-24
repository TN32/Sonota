package com.example.sonota.ui.rc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.sonota.ui.ec.UpdateExpenceFragment;

import java.util.ArrayList;


public class ReceiveListFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String year,month;

    public ReceiveListFragment() {
        // Required empty public constructor
        fabCount =1;
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
    public static ReceiveListFragment newInstance(String param1, String param2) {
        ReceiveListFragment fragment = new ReceiveListFragment();
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
        View root = inflater.inflate(R.layout.fragment_rc_list_receive, container, false);
        Bundle args = getArguments();

        TextView tv_rc_revenue =(TextView)root.findViewById(R.id.tv_rc_revenue);
        // 当月の予想収入を表示
        tv_rc_revenue.setText("50000");

        this.year = args.getString("selectedYear");
        this.month = args.getString("selectedMonth");

        String selected = year + "年" + month + "月";

        TextView textView = (TextView)root.findViewById(R.id.tv_rc_currentmonth);
        textView.setText(selected);


        Button prevbButton = (Button) root.findViewById(R.id.bt_rc_lastmonth);

        prevbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一覧画面へ値を渡す
                ReceiveListFragment fragment = new ReceiveListFragment();
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
                transaction.replace(R.id.rc_mainsection, fragment);
                // 戻るボタンで戻ってこれるように
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button nextbButton = (Button) root.findViewById(R.id.bt_rc_nextmonth);

        nextbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一覧画面へ値を渡す
                ReceiveListFragment fragment = new ReceiveListFragment();
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
                transaction.replace(R.id.rc_mainsection, fragment);
                // 戻るボタンで戻ってこれるように
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ArrayList<ReceiveClass> listData = new ArrayList<>();
        ReceiveClass data = new ReceiveClass(0, "Apple", 18000, 880);
        listData.add(data);


        final ReceiveListAdapter arrayAdapter = new ReceiveListAdapter(getContext(),listData,R.layout.list_rc_receive_cell);

        // idがlistのListViewを取得
        ListView listView = (ListView) root.findViewById(R.id.listview);
        listView.setOnTouchListener(new View.OnTouchListener() {
            int oldX = 0, oldY = 0;
            int originX,originY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = (int) event.getX();
                        oldY = (int) event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        originX = (int) event.getX();
                        originY = (int) event.getY();
                        onViewScroll(originX, originY, oldX, oldY);
                        //スクロールイベントの発生を知らせる
                        oldX = originX;
                        oldY = originY;

                        break;
                }

                return false;
            }
        });
        listView.setAdapter(arrayAdapter);

        // セルを選択されたら一覧画面フラグメント呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            }
        });

        return root;
    }

    public void onViewScroll(int orgX, int orgY, int oldX, int oldY)
    {
        if (oldY>orgY){
            if (fabCount == 1) {
                fabCount = 0;
                mFabControllInterface.setFabCount(fabCount);
            }
        }else {
            if (fabCount == 0) {
                fabCount = 1;
                mFabControllInterface.setFabCount(fabCount);
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
