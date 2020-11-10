package com.example.sonota.ui.set;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetParttimejobListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetParttimejobListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetParttimejobListFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ParttimejobListAdapter adapter;

    public SetParttimejobListFragment() {
        // Required empty public constructor
        fabCount = 1;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetParttimejobListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetParttimejobListFragment newInstance(String param1, String param2) {
        SetParttimejobListFragment fragment = new SetParttimejobListFragment();
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
        View root = inflater.inflate(R.layout.fragment_set_parttimejob_list, container, false);

        listView = root.findViewById(R.id.list_view);

        listload();

        //セルを選択された詳細画面フラグメントを呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                final String[] items = {"変更", "削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("選択").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //                             item_which pressed
                        switch (which) {
                            case 0:
                                // 詳細画面へ値を渡す
                                AddParttomejobFragment fragment = new AddParttomejobFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("selected", (int)adapter.getItemId(selectedPosition));
                                bundle.putString("Name",adapter.getItemName(selectedPosition));
                                bundle.putInt("Hwage", adapter.getItemHwagev(selectedPosition));
                                bundle.putString("Cday", adapter.getItemCday(selectedPosition));
                                bundle.putString("Pday", adapter.getItemPday(selectedPosition));

                                fragment.setArguments(bundle);
                                //詳細画面を呼び出す
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.set_mainsection, fragment);
                                //戻るボタンで戻ってこれるように
                                transaction.addToBackStack("SetParttimejobList");
                                transaction.commit();

                                break;
                            case 1:
                                final String[] items = { "削除する", "キャンセル"};
                                new AlertDialog.Builder(getActivity()).setTitle("本当にアルバイト先を削除しますか？").setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //                             item_which pressed
                                        switch (which) {
                                            case 0:
                                                adapter.getItemId(selectedPosition);
                                                String[] whereId = new String[1];
                                                whereId[0] = String.valueOf(adapter.getItemId(selectedPosition));
                                                db.delete(
                                                        "t_byteahead",
                                                        "byteahead_code=?",
                                                        whereId
                                                );
                                                listload();
                                                break;
                                            case 1:
                                                break;
                                        }

                                    }
                                }).show();

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("警告")
                                        .setMessage("アルバイト先を削除すると、そのアルバイト先の過去のシフトが正常に表示できなくなります。" )
                                        .setPositiveButton("次へ", null)
                                        .show();
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

    public void listload() {
        if (helper == null) {
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if (db == null) {
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
                new String[]{"byteahead_code","byteahead_name","byteahead_hwage", "byteahead_pday", "byteahead_cday"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        ArrayList<ParttimejobListDataClass> listData = new ArrayList<ParttimejobListDataClass>();

        for (int i = 0; i < cursor.getCount(); i++) {
            ParttimejobListDataClass data = new ParttimejobListDataClass(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            listData.add(data);
            cursor.moveToNext();
        }

        cursor.close();


        /**
         * CustomAdapterを生成
         * R.layout.custom_list_layout : リストビュー自身のレイアウト。今回は自作。
         */
        adapter = new ParttimejobListAdapter(
                getContext(),
                listData, // 使用するデータ
                R.layout.list_set_parttimejob_cell // 自作したレイアウト
        );

        listView.setAdapter(adapter);
    }

    @Override
    public void onFab1Clicked(int fabId){
        switch (fabId){
            case 0:
                AddParttomejobFragment fragment = new AddParttomejobFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.set_mainsection, fragment);
                //戻るボタンで戻ってこれるように
                transaction.addToBackStack("SetParttimejobList");
                transaction.commit();
                return;
        }
        Toast.makeText(getContext() , "このFragmentのFAB" + fabId + "は未実装です。", Toast.LENGTH_LONG).show();
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
