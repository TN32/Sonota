package com.example.sonota.ui.mec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class MecFragment extends Fragment {

    private MecViewModel mecViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mecViewModel =
                ViewModelProviders.of(this).get(MecViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mec, container, false);

        return root;
    }
}