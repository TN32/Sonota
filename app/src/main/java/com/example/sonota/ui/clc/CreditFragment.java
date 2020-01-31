package com.example.sonota.ui.clc;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class CreditFragment extends CustomFragment {

    public CreditFragment() {
        fabCount = 0;
    }
    CreditListAdapter adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_clc_credit, container, false);

        // idがlistのListViewを取得
        listView = (ListView) view.findViewById(R.id.list_view);

        listload();

        //セルを選択された詳細画面フラグメントを呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                int payAmount = adapter.getPayAmout(selectedPosition);
                final String[] items = {"支払い登録" , "変更", "削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("一度の支払い額:" + payAmount).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //item_which pressed
                        switch (which) {
                            case 0:
                                int payAmount = adapter.getPayAmout(selectedPosition);
                                final String[] items = {"登録する", "キャンセル"};
                                new AlertDialog.Builder(getActivity()).setTitle("分割支払いの登録をしますか？").setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //                             item_which pressed
                                        switch (which) {
                                            case 0:
                                                String memoText = "分割払いの支払い(" + adapter.getMemo(selectedPosition) + ")";
                                                int payAmount = adapter.getPayAmout(selectedPosition);

                                                ContentValues values = new ContentValues();
                                                values.put("payment_date", truncDate(new Date()));
                                                values.put("payment_money", payAmount);
                                                values.put("payment_memo", memoText);
                                                values.put("payment_cpay", true);

                                                db.insert("t_payment",null, values);

                                                if(adapter.getTimes(selectedPosition) - 1 > 0){
                                                    values = new ContentValues();
                                                    int i = adapter.getrAmount(selectedPosition) - payAmount;
                                                    values.put("partialr__amount", adapter.getrAmount(selectedPosition) - payAmount);
                                                    values.put("partial_times", adapter.getTimes(selectedPosition) - 1);
                                                    values.put("partial_cpay", true);
                                                    values.put("partial_pmemo", adapter.getMemo(selectedPosition));

                                                    int id = (int)adapter.getItemId(selectedPosition);

                                                    db.update("t_partial",values, "partial_code = " + id,null);
                                                }
                                                else{
                                                    adapter.getItemId(selectedPosition);
                                                    String[] whereId = new String[1];
                                                    whereId[0] = String.valueOf(adapter.getItemId(selectedPosition));
                                                    db.delete(
                                                            "t_partial",
                                                            "partial_code=?",
                                                            whereId
                                                    );
                                                }
                                                break;
                                            case 3:
                                                break;
                                        }
                                        listload();
                                    }
                                }).show();
                                break;
                            case 1:
                                // 詳細画面へ値を渡す
                                Fragment fragment = new DetailCerditFragment();
                                CreditListDataClass currentClass =(CreditListDataClass)adapter.getItem(selectedPosition);
                                Bundle bundle = new Bundle();
                                bundle.putInt("selected",(int)currentClass.getId());
                                bundle.putString("Name",currentClass.getMemo());
                                bundle.putInt("Amout",currentClass.getAmout());
                                bundle.putInt("Split",currentClass.getrAmount() / currentClass.getTimes());
                                bundle.putInt("PerM",currentClass.getrAmount());
                                bundle.putString("Remaining",String.valueOf(currentClass.getTimes()));

                                fragment.setArguments(bundle);
                                //詳細画面を呼び出す
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.clc_mainsection, fragment);
                                //戻るボタンで戻ってこれるように
                                transaction.addToBackStack("Credit");
                                transaction.commit();
                                break;
                            case 2:
                                adapter.getItemId(selectedPosition);
                                String[] whereId = new String[1];
                                whereId[0] = String.valueOf(adapter.getItemId(selectedPosition));
                                db.delete(
                                        "t_partial",
                                        "partial_code=?",
                                        whereId
                                );
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

    public void listload(){
        if (helper == null){
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }

        // ListViewに表示する項目を生成
        Cursor cursor = db.query(
                "t_partial",
                new String[]{"partial_code","partial_pmemo","partialr__amount","partial_amount","partial_times"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        ArrayList<CreditListDataClass> listData = new ArrayList<CreditListDataClass>();

        for (int i = 0; i < cursor.getCount(); i++) {
            CreditListDataClass data = new CreditListDataClass(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4));
            listData.add(data);
            cursor.moveToNext();
        }

        cursor.close();

        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new CreditListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_clc_credit_cell // 自作したレイアウト
        );

        listView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){


    }

    @Override
    public void onFab1Clicked(int fabId){
        switch (fabId){
            case 0:
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
