package com.example.sonota.ui.clc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class ClcFragment extends Fragment {

    private ClcViewModel clcViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clcViewModel =
                ViewModelProviders.of(this).get(ClcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clc, container, false);

        return root;
    }
}
