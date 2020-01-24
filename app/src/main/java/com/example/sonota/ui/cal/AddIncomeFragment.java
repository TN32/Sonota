package com.example.sonota.ui.cal;

import android.content.ContentValues;
import android.content.Context;
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
 * {@link AddIncomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIncomeFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View root;
    TextView textviewAddincomeToday;
    EditText edittextAddincomeMoney;
    EditText editTextAddincomeMemo;


    String addDate;

    public AddIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddIncomeFragment newInstance(String param1, String param2) {
        AddIncomeFragment fragment = new AddIncomeFragment();
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
        root = inflater.inflate(R.layout.fragment_add_income, container, false);

        super.onCreate(savedInstanceState);


        edittextAddincomeMoney = (EditText)root.findViewById(R.id.edittextAddincomeMoney);
        editTextAddincomeMemo = (EditText)root.findViewById(R.id.edittextAddincomeMemo);

        Button buttonAddincomeAdd = root.findViewById(R.id.bt_cal_registration);
        buttonAddincomeAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String memo = edittextAddincomeMoney.getText().toString();
                String date = textviewAddincomeToday.getText().toString();
                String money = editTextAddincomeMemo.getText().toString();


                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                insertData(db, money, date, memo);

                Toast.makeText(getContext(),  memo + "が完了しました!\n " + money + "," + date ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("CalenderContent", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if(args != null){
            addDate = args.getString("addDate");
            textviewAddincomeToday = root.findViewById(R.id.textviewAddincomeToday);
            textviewAddincomeToday.setText(sharpingDate(addDate));
        }

        return root;
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

    private void insertData(SQLiteDatabase db, String money, String day, String memo){
        ContentValues values = new ContentValues();
        values.put("income_money", money);
        values.put("income_day", addDate);
        values.put("income_memo", memo);

        db.insert("t_income",null, values);
    }
}
