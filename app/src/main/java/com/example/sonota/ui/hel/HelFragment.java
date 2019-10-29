package com.example.sonota.ui.hel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class HelFragment extends Fragment {

    private HelViewModel helViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helViewModel =
                ViewModelProviders.of(this).get(HelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hel, container, false);

        return root;
    }
}