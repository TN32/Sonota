package com.example.sonota.ui.clc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sonota.CustomFragment;
import com.example.sonota.FabControllInterface;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

public class DetailCerditFragment extends CustomFragment {

    EditText etName, etAmout, etSprit, etPerm, etRemaining ;
    boolean isNewItem = true;
    int id;

    private SonotaDBOpenHelper helper;
    private SQLiteDatabase db;

    public DetailCerditFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_clc_detailcredit, container, false);


        final Button bt_clc_credit_update = (Button)root.findViewById(R.id.bt_clc_credit_update);

        Bundle args = getArguments();
        if(args != null){
            id = args.getInt("selected");

            etName = (EditText)root.findViewById(R.id.etName);
            etName.setHint(args.getString("Name"));

            etAmout = (EditText)root.findViewById(R.id.etAmout);
            etAmout.setHint(String.valueOf(args.getInt("Amout")));

            etSprit = (EditText)root.findViewById(R.id.etSplit);
            etSprit.setHint(String.valueOf(args.getInt("Split")));

            etPerm = (EditText)root.findViewById(R.id.etPerM);
            etPerm.setHint(String.valueOf(args.getInt("PerM")));

            etRemaining = (EditText)root.findViewById(R.id.etRemaining);
            etRemaining.setHint(args.getString("Remaining"));

            isNewItem = false;

            bt_clc_credit_update.setText("更新");
        }


        bt_clc_credit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String amout = etAmout.getText().toString();
                String split = etSprit.getText().toString();
                String perm = etPerm.getText().toString();
                String remaining = etRemaining.getText().toString();
                Boolean capy = true;

                if(isNewItem ==true)
                {
                    if (helper == null){
                        helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                    }

                    if(db == null){
                        db = helper.getWritableDatabase();
                    }

                    insertData(db, amout, split, capy, remaining);

                    Toast.makeText(getContext(),  bt_clc_credit_update.getText() + "が完了しました!\n " + name + "," + amout ,Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack("Credit", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }

                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                insertData(db, amout, split, capy, remaining);

                Toast.makeText(getContext(),  bt_clc_credit_update.getText() + "が完了しました!\n " + name + "," + amout ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("Credit", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return root;
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

    private void insertData(SQLiteDatabase db, String amout, String split, boolean capy, String name){

        ContentValues values = new ContentValues();
        values.put("partial_money", amout);
        values.put("partial_fwithdrawel", split);
        values.put("partial_cpay", capy);
        values.put("partial_pmemo", name);

        db.insert("t_partial",null, values);
    }

    private void updateDate(SQLiteDatabase db, String amout, String split, boolean capy, String name){

        ContentValues values = new ContentValues();
        values.put("partial_money", amout);
        values.put("partial_fwithdrawel", split);
        values.put("partial_cpay", capy);
        values.put("partial_pmemo", name);

        db.update("t_partial",values, "partial_code = " + id,null);

    }

}