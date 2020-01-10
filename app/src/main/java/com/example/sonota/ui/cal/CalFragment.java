package com.example.sonota.ui.cal;

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

import com.example.sonota.R;

import static java.lang.Integer.parseInt;

public class CalFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cal, container, false);

        CalenderContentFragment fragment = new CalenderContentFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        // 一覧画面を呼び出す
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.cal_mainsection, fragment);
        transaction.commit();

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mFabControllInterface = (FabControllInterface)context;
//        if (context instanceof CalFragment.OnFragmentInteractionListener) {
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
        void onCreateCalFlagment(CalenderContentFragmentControllAdapter adapter);
    }
}