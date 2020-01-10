package com.example.sonota.ui.tmp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.CustomFragment;
import com.example.sonota.FabControllInterface;
import com.example.sonota.R;
import com.example.sonota.ui.ec.ExpenceFragment;

public class TmpFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FabControllInterface mFabControllInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tmp, container, false);

        PagerFragment mainFragment = new PagerFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tmp_mainsection,mainFragment);
        transaction.commit();

        return root;
    }

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
