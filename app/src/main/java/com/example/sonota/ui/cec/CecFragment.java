package com.example.sonota.ui.cec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class CecFragment extends Fragment {

    private CecViewModel cecViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cecViewModel =
                ViewModelProviders.of(this).get(CecViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cec, container, false);

        return root;
    }
}