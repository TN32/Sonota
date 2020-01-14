package com.example.sonota.ui.tmp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;
import java.util.EventListener;

public class EventFragment extends CustomFragment {

    public EventFragment(){
        fabCount = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tmp_event, container, false);

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
                "t_scheduletemplate",
                new String[]{"scheduletemplate_code","scheduletemplate_name","scheduletemplate_stime","scheduletemplate_etime"},
                null,
                null,
                null,
                null,
                "scheduletemplate_stime"
        );

        cursor.moveToFirst();
        ArrayList<EventListClass> listData = new ArrayList<EventListClass>();

        for (int i = 0; i < cursor.getCount(); i++) {
            EventListClass data = new EventListClass(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            listData.add(data);
            cursor.moveToNext();
        }

        cursor.close();


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
            case 0:
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
