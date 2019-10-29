package com.example.sonota.ui.set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class SetFragment extends Fragment {

    private SetViewModel setViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setViewModel =
                ViewModelProviders.of(this).get(SetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_set, container, false);

        return root;
    }
}