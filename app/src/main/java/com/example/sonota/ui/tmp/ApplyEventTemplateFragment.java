package com.example.sonota.ui.tmp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplyEventTemplateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplyEventTemplateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyEventTemplateFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<ApplyDateListClass> addDateList = new ArrayList<>();

    EditText etYear,etMonth,etDate;
    ApplyDateListAdapter adapter;


    TextView name1;

    String name;
    String startTime;
    String finishTime;

    public ApplyEventTemplateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyEventTemplateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyEventTemplateFragment newInstance(String param1, String param2) {
        ApplyEventTemplateFragment fragment = new ApplyEventTemplateFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_tmp_apply_event_template, container, false);

        listView = (ListView) root.findViewById(R.id.listview);

        etYear  = root.findViewById(R.id.et_tmp_apply_year);
        etMonth  = root.findViewById(R.id.et_tmp_apply_month);
        etDate  = root.findViewById(R.id.et_tmp_apply_date);

        String[] today = splitDate(truncDate(new Date()));
        etYear.setText(String.valueOf(Integer.valueOf(today[0])));
        etMonth.setText(String.valueOf(Integer.valueOf(today[1])));
        etDate.setText(String.valueOf(Integer.valueOf(today[2])));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                selectedPosition = position;
                final String[] items = {"削除", "キャンセル"};
                new AlertDialog.Builder(getActivity()).setTitle("Selector").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                addDateList.remove(selectedPosition);
                                listLoad();
                                break;
                            case 1:
                                break;
                        }
                    }
                }).show();
            }
        });

        Button btAddItem = root.findViewById(R.id.bt_tmp_add_item);
        btAddItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String year = etYear.getText().toString();
                String month = etMonth.getText().toString();
                String date = etDate.getText().toString();
                String[] today = splitDate(truncDate(new Date()));

                if (year.equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("年が入力されていません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                else if(Integer.valueOf(year) > Integer.valueOf(today[0]) + 10 || Integer.valueOf(year) < Integer.valueOf(today[0]) - 10){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("今年から10年以内の年を指定してください。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                if (month.equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("月が入力されていません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                else if(Integer.valueOf(month) > 12 || Integer.valueOf(month) < 1){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage(month + " 月は存在しません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }


                if (month.equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("月が入力されていません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                else if(Integer.valueOf(month) > 12 || Integer.valueOf(month) < 1){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage(month + " 月は存在しません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                if (date.equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("日が入力されていません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.valueOf(year),Integer.valueOf(month) - 1,1);
                int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                if(Integer.valueOf(date) < 1 || Integer.valueOf(date) > lastDay){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage(month + " 月" + date + "日は存在しません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                if(Integer.valueOf(year) < 1000){
                    year = "0" + Integer.valueOf(year);
                }

                if(Integer.valueOf(month) < 10){
                    month = "0" + Integer.valueOf(month);
                }

                if(Integer.valueOf(date) < 10){
                    date = "0" + Integer.valueOf(date);
                }

                ApplyDateListClass addDats = new ApplyDateListClass(year + "_" + month + "_" + date);

                addDateList.add(addDats);
                listLoad();
                if(lastDay == Integer.valueOf(date)){
                    etMonth.setText(String.valueOf(Integer.valueOf(month) + 1));
                    etDate.setText("1");
                }
                else {
                    etDate.setText(String.valueOf(Integer.valueOf(date) + 1));
                }
            }
        });

        Button btApply = root.findViewById(R.id.bt_tmp_add_schesule);
        btApply.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(addDateList.size() == 0)
                    return;

                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                for(int i = 0;i < adapter.getCount();i++){
                    insertData(db,adapter.getDate(i));
                }

                Toast.makeText(getContext(),"選択日に登録されました!" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("Event", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });

        Bundle args = getArguments();
        if(args != null){
            name1 = (TextView) root.findViewById(R.id.tv_tmp_event_detail);

            name = args.getString("Name");
            startTime = args.getString("starttime");
            finishTime = args.getString("finishtime");

            String start[] = startTime.split("_");
            String finish[] = finishTime.split("_");

            name1.setText(name + ":" + start[0] + "時" + start[1] + "分～" + finish[0] +  "時" + finish[1] + "分");
        }

        return root;
    }

    @Override
    public boolean onBackPressed(){
        final String[] items = { "破棄する", "このページに留まる"};
        new AlertDialog.Builder(getActivity()).setTitle("登録内容を破棄しますか？").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //                             item_which pressed
                switch (which) {
                    case 0:
                        Toast.makeText(getContext(),"登録内容が破棄されました。" ,Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack("Event", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        break;
                    case 1:
                        break;
                }

            }
        }).show();

        return true;
    }

    public void listLoad(){
        adapter = new ApplyDateListAdapter(
                getContext(),
                addDateList,
                R.layout.list_tmp_date_apply_cell);

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

    private void insertData(SQLiteDatabase db, String addDate){
        ContentValues values = new ContentValues();
        values.put("schedule_name", name);
        values.put("schedule_day", addDate);
        values.put("schedule_stime", startTime);
        values.put("schedule_etime", finishTime);

        db.insert("t_schedule",null, values);
    }
}
