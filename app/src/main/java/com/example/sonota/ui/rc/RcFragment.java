package com.example.sonota.ui.rc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class RcFragment extends Fragment {

    private RcViewModel rcViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rcViewModel =
                ViewModelProviders.of(this).get(RcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rc, container, false);

        return root;
    }
}