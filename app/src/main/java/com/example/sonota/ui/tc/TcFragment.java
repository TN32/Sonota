package com.example.sonota.ui.tc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sonota.R;

public class TcFragment extends Fragment {

    private TcModel tcModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tcModel =
                ViewModelProviders.of(this).get(TcModel.class);
        View root = inflater.inflate(R.layout.fragment_tc, container, false);

        return root;
    }
}