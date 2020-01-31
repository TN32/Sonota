package com.example.sonota.ui.clc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sonota.FabControllInterface;
import com.example.sonota.R;

public class ClcFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clc, container, false);

        CreditFragment fragment = new CreditFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.clc_mainsection, fragment);
        // 戻るボタンで戻ってこれるように
        transaction.addToBackStack(null);
        transaction.commit();

        return root;
    }

    private OnFragmentInteractionListener mListener;
    private FabControllInterface mFabControllInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFabControllInterface = (FabControllInterface)context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
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
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
