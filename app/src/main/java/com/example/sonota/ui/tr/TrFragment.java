package com.example.sonota.ui.tr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class TrFragment extends Fragment {

    private TrViewModel trViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trViewModel =
                ViewModelProviders.of(this).get(TrViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tr, container, false);

        return root;
    }
}