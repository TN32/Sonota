package com.example.sonota.ui.tmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class TmpFragment extends Fragment {

    private TmpViewModel tmpViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tmpViewModel =
                ViewModelProviders.of(this).get(TmpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tmp, container, false);

        return root;
    }
}