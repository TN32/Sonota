package com.example.sonota.ui.ec;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateExpenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateExpenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateExpenceFragment extends CustomFragment {
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
    RadioGroup radiogroup;


    public UpdateExpenceFragment() {
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
    public static UpdateExpenceFragment newInstance(String param1, String param2) {
        UpdateExpenceFragment fragment = new UpdateExpenceFragment();
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
        root = inflater.inflate(R.layout.fragment_ec_update_expence, container, false);

        super.onCreate(savedInstanceState);

        et_caladd_insnumber = (EditText)root.findViewById(R.id.et_ec_update_insnumber);
        et_caladd_memo = (EditText)root.findViewById(R.id.et_ec_update_memo);
        et_cal_add_money = (EditText)root.findViewById(R.id.et_ec_update_money);

        radiogroup = (RadioGroup)root.findViewById(R.id.radiogroup);


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton radio = (RadioButton)root.findViewById(R.id.rb_ec_update_cash);
                RadioButton radio1 = (RadioButton)root.findViewById(R.id.rb_ec_update_installments);
                RadioButton radio2 = (RadioButton)root.findViewById(R.id.rb_ec_update_noinstallments);
                TextView textView = (TextView)root.findViewById(R.id.textView9);
                LinearLayout linearLayout = (LinearLayout)root.findViewById(R.id.linearlayout1);
                LinearLayout ll_caladd_ccdetail = (LinearLayout)root.findViewById(R.id.ll_caladd_ccdetail);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)ll_caladd_ccdetail.getLayoutParams();

                if (id == R.id.rb_ec_update_cash) {
                    isCreditPayment = false;
                } else if(id == R.id.rb_ec_update_cc){
                    isCreditPayment = true;
                }
            }
        });

        Button bt_cal_registration = root.findViewById(R.id.bt_ec_registration);
        bt_cal_registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (et_cal_add_money.getText().toString().equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("金額が入力されていません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

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
                    updateMoney(db,addDate,money,memo);
                }else{
                    updateCredit(db,addDate,money,memo);
                }

                Toast.makeText(getContext(),  "登録が完了しました!" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("PaymentList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if(args != null){
            selected = args.getInt("selected");
            addDate = args.getString("addDate");
            tv_cal_adddate = root.findViewById(R.id.tv_ec_adddate);
            tv_cal_adddate.setText(sharpingDate(addDate));

            et_caladd_memo.setText(args.getString("Memo"));
            et_cal_add_money.setText(String.valueOf(args.getInt("Amount")));

            if(args.getString("isCredit").equals("true"))
            {
                radiogroup.check(R.id.rb_ec_update_cc);
            }
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
                        getActivity().getSupportFragmentManager().popBackStack("PaymentList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

    private void updateMoney(SQLiteDatabase db, String date, String money, String memo){
        ContentValues values = new ContentValues();
        values.put("payment_date", date);
        values.put("payment_money", money);
        values.put("payment_memo", memo);
        values.put("payment_cpay", false);

        String[] whereArgs = {String.valueOf(selected)};

        db.update("t_payment",values, "payment_code=?",whereArgs);
    }

    private void updateCredit(SQLiteDatabase db, String date, String money, String memo){
        ContentValues values = new ContentValues();
        values.put("payment_date", date);
        values.put("payment_money", money);
        values.put("payment_memo", memo);
        values.put("payment_cpay", true);

        String[] whereArgs = {String.valueOf(selected)};

        db.update("t_payment",values, "payment_code=?",whereArgs);
    }
}
