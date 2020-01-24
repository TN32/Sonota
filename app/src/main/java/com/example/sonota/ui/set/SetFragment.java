package com.example.sonota.ui.set;

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
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.CustomFragment;
import com.example.sonota.FabControllInterface;
import com.example.sonota.R;
import com.example.sonota.ui.cal.CalenderContentFragment;
import com.example.sonota.ui.ec.EcFragment;

public class SetFragment extends CustomFragment {


    private OnFragmentInteractionListener mListener;
    private FabControllInterface mFabControllInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set, container, false);

        SetParttimejobListFragment fragment = new SetParttimejobListFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        //一覧画面を呼び出す
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.set_mainsection, fragment);
        transaction.commit();


        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mFabControllInterface = (FabControllInterface)context;
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
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
