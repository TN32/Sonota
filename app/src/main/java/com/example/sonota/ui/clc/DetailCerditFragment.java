package com.example.sonota.ui.clc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sonota.CustomFragment;
import com.example.sonota.FabControllInterface;
import com.example.sonota.R;

public class DetailCerditFragment extends CustomFragment {

    EditText name, amout, sprit, perm, remaining;

    public DetailCerditFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_clc_detailcredit, container, false);


        final Button bt_clc_credit_update = (Button)root.findViewById(R.id.bt_clc_credit_update);

        Bundle args = getArguments();
        if(args != null){
            int selected = args.getInt("selected");

            name = (EditText)root.findViewById(R.id.etName);
            name.setHint(args.getString("Name1"));

            amout = (EditText)root.findViewById(R.id.etAmout);
            amout.setHint(args.getString("Amout1"));

            sprit = (EditText)root.findViewById(R.id.etSplit);
            sprit.setHint(args.getString("Split1"));

            perm = (EditText)root.findViewById(R.id.etPerM);
            perm.setHint(args.getString("PerM1"));

            remaining = (EditText)root.findViewById(R.id.etRemaining);
            remaining.setHint(args.getString("Remaining1"));

            bt_clc_credit_update.setText("更新");
        }

        bt_clc_credit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),  bt_clc_credit_update.getText() + "が完了しました!",Toast.LENGTH_SHORT).show();
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

}