package com.example.sonota.ui.cal;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;

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
       

        RadioGroup radiogroup = (RadioGroup)root.findViewById(R.id.radiogroup);
        RadioGroup radiogroup2 = (RadioGroup)root.findViewById(R.id.radiogroup2);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                RadioButton radio = (RadioButton)root.findViewById(R.id.radioButton);
                RadioButton radio1 = (RadioButton)root.findViewById(R.id.radioButton3);
                RadioButton radio2 = (RadioButton)root.findViewById(R.id.radioButton4);
                EditText etext = (EditText)root.findViewById(R.id.editText4);
                TextView textView= (TextView)root.findViewById(R.id.textView9);
                LinearLayout linearLayout = (LinearLayout)root.findViewById(R.id.linearlayout1);
                LinearLayout aaaa = (LinearLayout)root.findViewById(R.id.aaaa);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)aaaa.getLayoutParams();

                if (id == R.id.radioButton) {
                    radio1.setEnabled(false);
                    radio2.setEnabled(false);
                    etext.setEnabled(false);
                    textView.setTextColor(Color.argb(255,128,128,128));
                    linearLayout.setBackgroundColor(Color.argb(255,169,169,169));
                    params.height = 0;
                    aaaa.setLayoutParams(params);
                } else if(id == R.id.radioButton2){
                    radio1.setEnabled(true);
                    radio2.setEnabled(true);
                    etext.setEnabled(true);
                    textView.setTextColor(Color.parseColor("#160000"));
                    linearLayout.setBackgroundColor(Color.WHITE);
                    params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    aaaa.setLayoutParams(params);
                }
            }
        });
        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            public void onCheckedChanged(RadioGroup radioGroup, int id){
                RadioButton radio1 = (RadioButton)root.findViewById(R.id.radioButton4);
                RadioButton radio2 = (RadioButton)root.findViewById(R.id.radioButton3);
                EditText etext = (EditText)root.findViewById(R.id.editText4);
                TextView textView= (TextView)root.findViewById(R.id.textView9);
                if(id == R.id.radioButton4){
                    textView.setTextColor(Color.argb(255,128,128,128));

                    etext.setEnabled(false);

                } else if(id == R.id.radioButton3){
                    etext.setEnabled(true);
                    textView.setTextColor(Color.parseColor("#160000"));

                }
            }
        });
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
}
