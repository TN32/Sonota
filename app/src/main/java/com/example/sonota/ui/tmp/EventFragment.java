package com.example.sonota.ui.tmp;

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
import com.example.sonota.ui.clc.DetailLoanFragment;

import java.util.ArrayList;

public class EventFragment extends CustomFragment {

    public EventFragment(){
        fabCount = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tmp_event, container, false);
        ArrayList<EventListClass> listData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            EventListClass data = new EventListClass(i,"イベント名" + i, i + "時0分","23時" + i + "分");
            listData.add(data);
        }

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
         final EventListAdapter adapter = new EventListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_tmp_event_cell // 自作したレイアウト
        );

        // idがlistのListViewを取得
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //セルを選択された詳細画面フラグメントを呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 詳細画面へ値を渡す
                DetailEventFragment fragment = new DetailEventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("selected", position);
                bundle.putString("starttime", adapter.getCurrentStartTime(position));
                bundle.putString("finishtime", adapter.getCurrentFinishTime(position));

                fragment.setArguments(bundle);
                //詳細画面を呼び出す
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.tmp_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Event");
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        isSetToCurrentFragment = false;
        super.onResume();
    }

    @Override
    public void onFab1Clicked(int fabId){
        switch (fabId){
            case 1:
                DetailEventFragment fragment = new DetailEventFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.tmp_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Event");
                transaction.commit();
                return;
        }
        Toast.makeText(getContext() , "このFragmentのFAB" + fabId + "は未実装です。", Toast.LENGTH_LONG).show();
    }
}
