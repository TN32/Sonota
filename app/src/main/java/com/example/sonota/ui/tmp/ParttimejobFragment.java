package com.example.sonota.ui.tmp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.sonota.ui.cal.ParttimejobPlaceClass;

import java.util.ArrayList;

public class ParttimejobFragment extends CustomFragment {

    ParttimeJobListAdapter adapter;
    ListView listView;

    public ParttimejobFragment(){
        fabCount = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tmp_event, container, false);


        listView = (ListView) view.findViewById(R.id.list_view);
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

        listload();

        //セルを選択された詳細画面フラグメントを呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                final String[] items = {"カレンダー上に登録する","変更", "削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("選択").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                             item_which pressed
                        CustomFragment fragment;
                        Bundle bundle = new Bundle();
                        bundle.putInt("selected",selectedPosition);
                        bundle.putString("Name",adapter.getCurrentTitle(selectedPosition));
                        bundle.putInt("PJID",adapter.getCurrentPJId(selectedPosition));
                        bundle.putString("starttime", adapter.getCurrentStartTime(selectedPosition));
                        bundle.putString("finishtime", adapter.getCurrentFinishTime(selectedPosition));
                        bundle.putInt("breaktime", adapter.getCurrentBreakTime(selectedPosition));

                        //詳細画面を呼び出す
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        switch (which) {
                            case 0:
                                fragment = new ApplyPartTimeJobTemplateFragment();
                                bundle.putInt("selected", (int)adapter.getItemId(selectedPosition));
                                bundle.putString("Name",adapter.getCurrentTitle(selectedPosition));
                                bundle.putString("starttime", adapter.getCurrentStartTime(selectedPosition));
                                bundle.putString("finishtime", adapter.getCurrentFinishTime(selectedPosition));
                                bundle.putInt("bteaktime",adapter.getCurrentBreakTime(selectedPosition));

                                fragment.setArguments(bundle);
                                //詳細画面を呼び出す
                                transaction.replace(R.id.tmp_mainsection, fragment);
                                //戻るボタンで戻ってこれるように
                                transaction.addToBackStack("Parttimejob");
                                transaction.commit();
                                break;
                            case 1:
                                // 詳細画面へ値を渡す
                                fragment = new DetailParttimejobFragment();

                                fragment.setArguments(bundle);
                                //戻るボタンで戻ってこれるように
                                transaction.addToBackStack("Parttimejob");
                                transaction.commit();
                                transaction.replace(R.id.tmp_mainsection, fragment);
                                break;
                            case 2:
                                final String[] item = { "削除する", "キャンセル"};
                                new AlertDialog.Builder(getActivity()).setTitle("本当に削除しますか？").setItems(item, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //                             item_which pressed
                                        switch (which) {
                                            case 0:
                                                adapter.getItemId(selectedPosition);
                                                String[] whereId = new String[1];
                                                whereId[0] = String.valueOf(adapter.getItemId(selectedPosition));
                                                db.delete(
                                                        "t_Parttimejobtemplate",
                                                        "Parttimejobtemplate_code=?",
                                                        whereId
                                                );
                                                listload();
                                            case 1:
                                                break;
                                        }
                                    }
                                }).show();
                                break;
                            case 3:
                                break;
                        }
                    }
                }).show();
            }
        });
        return view;
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
                "t_Parttimejobtemplate",
                new String[]{"Parttimejobtemplate_code","byteahead_code","Parttimejobtemplate_stime","Parttimejobtemplate_etime","Parttimejobtemplate_btime"},
                null,
                null,
                null,
                null,
                "Parttimejobtemplate_stime"
        );

        cursor.moveToFirst();
        ArrayList<ParttimeJobListClass> listData = new ArrayList<ParttimeJobListClass>();

        for (int i = 0; i < cursor.getCount(); i++) {
            ParttimeJobListClass data = new ParttimeJobListClass(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),pPlace);
            listData.add(data);
            cursor.moveToNext();
        }

        cursor.close();

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new ParttimeJobListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_tmp_parttimejob_cell // 自作したレイアウト
        );

        // idがlistのListViewを取得
        listView.setAdapter(adapter);
    }


    @Override
    public void onFab1Clicked(int fabId){
        switch (fabId){
            case 0:
                DetailParttimejobFragment fragment = new DetailParttimejobFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.tmp_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("Parttimejob");
                transaction.commit();
                return;
        }
        Toast.makeText(getContext() , "このFragmentのFAB" + fabId + "は未実装です。", Toast.LENGTH_LONG).show();
    }
}
