package com.example.sonota.ui.cal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddExpenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpenceFragment extends CustomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    View root;
    TextView tv_cal_adddate;
    EditText et_caladd_insnumber;
    EditText et_caladd_memo;
    EditText et_cal_add_money;
    boolean isCreditPayment = false;
    boolean isPartialPayment = false;

    String addDate;


    public AddExpenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddExpenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddExpenceFragment newInstance(String param1, String param2) {
        AddExpenceFragment fragment = new AddExpenceFragment();
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
        root = inflater.inflate(R.layout.fragment_cal_add_expence, container, false);

        super.onCreate(savedInstanceState);

        et_caladd_insnumber = (EditText)root.findViewById(R.id.et_caladd_insnumber);
        et_caladd_memo = (EditText)root.findViewById(R.id.et_caladd_memo);
        et_cal_add_money = (EditText)root.findViewById(R.id.et_cal_add_money);

        RadioGroup radiogroup = (RadioGroup)root.findViewById(R.id.radiogroup);
        RadioGroup radiogroup2 = (RadioGroup)root.findViewById(R.id.radiogroup2);


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                RadioButton radio = (RadioButton)root.findViewById(R.id.rb_caladd_cash);
                RadioButton radio1 = (RadioButton)root.findViewById(R.id.rb_caladd_installments);
                RadioButton radio2 = (RadioButton)root.findViewById(R.id.rb_caladd_noinstallments);
                TextView textView = (TextView)root.findViewById(R.id.textView9);
                LinearLayout linearLayout = (LinearLayout)root.findViewById(R.id.linearlayout1);
                LinearLayout ll_caladd_ccdetail = (LinearLayout)root.findViewById(R.id.ll_caladd_ccdetail);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)ll_caladd_ccdetail.getLayoutParams();

                if (id == R.id.rb_caladd_cash) {
                    radio1.setEnabled(false);
                    radio2.setEnabled(false);
                    et_caladd_insnumber.setEnabled(false);
                    textView.setTextColor(Color.argb(255,128,128,128));
                    linearLayout.setBackgroundColor(Color.argb(255,169,169,169));
                    params.height = 0;
                    ll_caladd_ccdetail.setLayoutParams(params);
                    isCreditPayment = false;
                } else if(id == R.id.rb_caladd_cc){
                    radio1.setEnabled(true);
                    radio2.setEnabled(true);
                    et_caladd_insnumber.setEnabled(true);
                    textView.setTextColor(Color.parseColor("#160000"));
                    linearLayout.setBackgroundColor(Color.WHITE);
                    params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    ll_caladd_ccdetail.setLayoutParams(params);
                    isCreditPayment = true;
                }
            }
        });

        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            public void onCheckedChanged(RadioGroup radioGroup, int id){
                RadioButton radio1 = (RadioButton)root.findViewById(R.id.rb_caladd_noinstallments);
                RadioButton radio2 = (RadioButton)root.findViewById(R.id.rb_caladd_installments);
                et_caladd_insnumber = (EditText)root.findViewById(R.id.et_caladd_insnumber);
                TextView textView= (TextView)root.findViewById(R.id.textView9);
                if(id == R.id.rb_caladd_noinstallments){
                    textView.setTextColor(Color.argb(255,128,128,128));
                    isPartialPayment = false;
                    et_caladd_insnumber.setEnabled(false);
                } else if(id == R.id.rb_caladd_installments){
                    et_caladd_insnumber.setEnabled(true);
                    textView.setTextColor(Color.parseColor("#160000"));
                    isPartialPayment = true;
                }
            }
        });

        Button bt_cal_registration = root.findViewById(R.id.bt_cal_registration);
        bt_cal_registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String insnumber = et_caladd_insnumber.getText().toString();
                String memo = et_caladd_memo.getText().toString();
                String money = et_cal_add_money.getText().toString();

                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                if(!isCreditPayment){
                    insertDateMoney(db,addDate,money,memo);
                }else if(!isPartialPayment){
                    insertDateCredit(db,addDate,money,memo);
                }else {
                    insertDatePartial(db,addDate,money,insnumber,memo);
                }

                Toast.makeText(getContext(),  "登録が完了しました!" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("CalenderContent", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if(args != null){
            addDate = args.getString("addDate");
            tv_cal_adddate = root.findViewById(R.id.tv_cal_adddate);
            tv_cal_adddate.setText(sharpingDate(addDate));
        }

        // Inflate the layout for this fragment
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

    private void insertDateMoney(SQLiteDatabase db, String date, String money, String memo){
        ContentValues values = new ContentValues();
        values.put("payment_date", date);
        values.put("payment_money", money);
        values.put("payment_memo", memo);
        values.put("payment_cpay", false);

        db.insert("t_payment",null, values);
    }

    private void insertDateCredit(SQLiteDatabase db, String date, String money, String memo){
        ContentValues values = new ContentValues();
        values.put("payment_date", date);
        values.put("payment_money", money);
        values.put("payment_memo", memo);
        values.put("payment_cpay", true);

        db.insert("t_payment",null, values);
    }

    private void insertDatePartial(SQLiteDatabase db, String date, String money, String fwithdrawel, String memo){
        ContentValues values = new ContentValues();
        values.put("partial_money", money);
        values.put("partial_fwithdrawel", fwithdrawel);
        values.put("partial_pmemo", memo);
        values.put("partial_cpay", true);

        db.insert("t_partial",null, values);
    }

}
