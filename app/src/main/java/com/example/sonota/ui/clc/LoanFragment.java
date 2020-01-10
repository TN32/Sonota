package com.example.sonota.ui.clc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.CustomFragment;
import com.example.sonota.FabControllInterface;
import com.example.sonota.R;

import java.util.ArrayList;

public class LoanFragment extends CustomFragment {

    public LoanFragment(){
        fabCount = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_clc_loan, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        ArrayList<LoanListDataClass> listData = new ArrayList<>();
        for(int i = 1; i <=  5; i++){
            switch (i){
                case 1:
                    LoanListDataClass data = new LoanListDataClass(i, "ギター", "残り"+"275000"+"円"+" "+"/"+" "+"月"+"25000"+"円", "残り"+"11"+"回");
                    listData.add(data);
                    break;
                case 2:
                    LoanListDataClass data1 = new LoanListDataClass(i, "ベース", "残り"+"100000"+"円"+" "+"/"+" "+"月"+"50000"+"円", "残り"+"2"+"回");
                    listData.add(data1);
                    break;
                case 3:
                    LoanListDataClass data2 = new LoanListDataClass(i, "バイク", "残り"+"320000"+"円"+" "+"/"+" "+"月"+"80000"+"円", "残り"+"4"+"回");
                    listData.add(data2);
                    break;
                case 4:
                    LoanListDataClass data3 = new LoanListDataClass(i, "NintendoSwitch", "残り"+"24000"+"円"+" "+"/"+" "+"月"+"12000"+"円", "残り"+"2"+"回");
                    listData.add(data3);
                    break;
                case 5:
                    LoanListDataClass data4 = new LoanListDataClass(i, "PlayStation4", "残り"+"24000"+"円"+" "+"/"+" "+"月"+"12000"+"円", "残り"+"2"+"回");
                    listData.add(data4);
                    break;
            }

        }

        //ListViewに表示するデータ
        LoanListAdapter loanListAdapter = new LoanListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_clc_loan_cell // 自作したレイアウト
        );

        // idがlistのListViewを取得
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(loanListAdapter);


        //セルを選択された詳細画面フラグメントを呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 詳細画面へ値を渡す
                DetailLoanFragment fragment = new DetailLoanFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("selected",position);
                bundle.putString("Name","ベース");
                bundle.putString("Amout","300000");
                bundle.putString("Split","12");
                bundle.putString("PerM","25000");
                bundle.putString("Remaining","11");
                bundle.putString("Name1","ギター");
                bundle.putString("Amout1","200000");
                bundle.putString("Split1","4");
                bundle.putString("PerM1","50000");
                bundle.putString("Remaining1","2");
                bundle.putString("Name2","バイク");
                bundle.putString("Amout2","400000");
                bundle.putString("Split2","5");
                bundle.putString("PerM2","80000");
                bundle.putString("Remaining2","4");
                bundle.putString("Name3","NintendoSwitch");
                bundle.putString("Amout3","36000");
                bundle.putString("Split3","3");
                bundle.putString("PerM3","12000");
                bundle.putString("Remaining3","2");
                bundle.putString("Name4","PlayStation4");
                bundle.putString("Amout4","36000");
                bundle.putString("Split4","3");
                bundle.putString("PerM4","12000");
                bundle.putString("Remaining4","2");

                fragment.setArguments(bundle);
                //詳細画面を呼び出す
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.clc_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Loan");
                transaction.commit();
            }
        });
    }

    @Override
    public void onFab1Clicked(int fabId){
        switch (fabId){
            case 1:
                DetailLoanFragment fragment = new DetailLoanFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.clc_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Loan");
                transaction.commit();
                return;
        }
        Toast.makeText(getContext() , "このFragmentのFAB" + fabId + "は未実装です。", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
//        }
    }


    @Override
    public void onResume() {
        isSetToCurrentFragment = false;
        super.onResume();
    }

    //表示されたときイベント
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

