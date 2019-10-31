package com.example.sonota.ui.ec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class EcFragment extends Fragment {

    private EcViewModel ecViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ecViewModel =
                ViewModelProviders.of(this).get(EcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ec, container, false);

        return root;
    }
}