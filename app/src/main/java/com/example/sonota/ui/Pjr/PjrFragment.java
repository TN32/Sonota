package com.example.sonota.ui.Pjr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class PjrFragment extends Fragment {

    private PjrViewModel pjrViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pjrViewModel =
                ViewModelProviders.of(this).get(PjrViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pjr, container, false);

        return root;
    }
}