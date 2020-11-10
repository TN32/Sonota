package com.example.sonota.ui.rc;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateReceiveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateReceiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateReceiveFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View root;
    EditText edittextAddincomeMoney;
    EditText edittextAddincomeMemo;
    TextView textviewAddincomeToday;

    String addDate;

    private OnFragmentInteractionListener mListener;

    public UpdateReceiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateReceiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateReceiveFragment newInstance(String param1, String param2) {
        UpdateReceiveFragment fragment = new UpdateReceiveFragment();
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

        root = inflater.inflate(R.layout.fragment_update_receive, container, false);
        super.onCreate(savedInstanceState);

        edittextAddincomeMemo = (EditText)root.findViewById(R.id.edittextAddincomeMemo);
        edittextAddincomeMoney = (EditText)root.findViewById(R.id.edittextAddincomeMoney);
        textviewAddincomeToday = (TextView)root.findViewById(R.id.textviewAddincomeToday);

        Button bt_cal_registration = root.findViewById(R.id.bt_cal_registration);

        bt_cal_registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                String memo = edittextAddincomeMemo.getText().toString();
                String money = edittextAddincomeMoney.getText().toString();

                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                updateIncome(db,addDate,money,memo);

                Toast.makeText(getContext(),  "登録が完了しました!" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("IncomeList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if(args != null){
            selected = args.getInt("selected");
            addDate = args.getString("addDay");
            textviewAddincomeToday = root.findViewById(R.id.textviewAddincomeToday);
            textviewAddincomeToday.setText(sharpingDate(addDate));

            edittextAddincomeMemo.setText(args.getString("Memo"));
            edittextAddincomeMoney.setText(String.valueOf(args.getInt("Money")));


        }
        // Inflate the layout for this fragment
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
                        getActivity().getSupportFragmentManager().popBackStack("IncomeList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        break;
                    case 1:
                        break;
                }

            }
        }).show();

        return true;
    }

    int selected;

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//
//    }  if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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

    private void updateIncome(SQLiteDatabase db,String day, String money, String memo){
        ContentValues values = new ContentValues();
        values.put("income_day", day);
        values.put("income_money", money);
        values.put("income_memo", memo);


        String[] whereArgs = {String.valueOf(selected)};

        db.update("t_income",values, "income_code=?",whereArgs);
    }
}
