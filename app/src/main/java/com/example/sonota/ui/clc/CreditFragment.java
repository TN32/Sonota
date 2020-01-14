package com.example.sonota.ui.clc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;

import java.util.ArrayList;

public class CreditFragment extends CustomFragment {

    public CreditFragment() {
        fabCount = 1;
    }
    CreditListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_clc_credit, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        // ListViewに表示する項目を生成
        ArrayList<CreditListDataClass> listData = new ArrayList<>();
        for(int i = 1; i <=  5; i++){
            switch (i){
                case 1:
                    CreditListDataClass data = new CreditListDataClass(R.mipmap.ic_launcher, "ベース", 275000, 11);
                    listData.add(data);
                    break;
                case 2:
                    CreditListDataClass data1 = new CreditListDataClass(R.mipmap.ic_launcher, "ギター", 100000, 2);
                    listData.add(data1);
                    break;
                case 3:
                    CreditListDataClass data2 = new CreditListDataClass(R.mipmap.ic_launcher, "バイク", 32000, 4);
                    listData.add(data2);
                    break;
                case 4:
                    CreditListDataClass data3 = new CreditListDataClass(R.mipmap.ic_launcher, "NintendoSwitch", 24000, 2);
                    listData.add(data3);
                    break;
                case 5:
                    CreditListDataClass data4 = new CreditListDataClass(R.mipmap.ic_launcher, "PlayStation4", 24000, 2);
                    listData.add(data4);
                    break;
            }

        }

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new CreditListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_clc_credit_cell // 自作したレイアウト
        );

        // idがlistのListViewを取得
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        //セルを選択された詳細画面フラグメントを呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 詳細画面へ値を渡す
                Fragment fragment = new DetailCerditFragment();
                CreditListDataClass currentClass =(CreditListDataClass)adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putInt("selected",(int)currentClass.getId());
                bundle.putString("Name",currentClass.getTitle());
                bundle.putInt("Amout",currentClass.getAmout());
                bundle.putInt("Split",currentClass.getCount());
                bundle.putInt("PerM",currentClass.getAmout() / currentClass.getCount());
                bundle.putString("Remaining","11");

                fragment.setArguments(bundle);
                //詳細画面を呼び出す
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.clc_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Credit");
                transaction.commit();
            }
        });
    }

    @Override
    public void onFab1Clicked(int fabId){
        switch (fabId){
            case 1:
                DetailCerditFragment fragment = new DetailCerditFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.clc_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Credit");
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
