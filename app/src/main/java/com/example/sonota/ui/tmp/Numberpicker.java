package com.example.sonota.ui.tmp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sonota.R;

public class Numberpicker extends AppCompatActivity {

    private NumberPicker numPicker0, numPicker1, numPicker2, numPicker3;
    private TextView pickerTextView;

    private String[] figures = new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickerTextView = findViewById(R.id.text_view);

        numPicker0 = findViewById(R.id.np_tmp_startTime1);
        numPicker1 = findViewById(R.id.np_tmp_startTime2);
        numPicker2 = findViewById(R.id.np_tmp_endTime1);
        numPicker3 = findViewById(R.id.np_tmp_endTime2);

        Button pickerButton = findViewById(R.id.bt_tmp_registration);

        numPicker0.setMaxValue(23);
        numPicker0.setMinValue(0);

        numPicker1.setMaxValue(59);
        numPicker1.setMinValue(0);

        numPicker2.setMaxValue(23);
        numPicker2.setMinValue(0);

        numPicker3.setMaxValue(59);
        numPicker3.setMinValue(0);



        pickerButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                figures[0] = String.valueOf(numPicker0.getValue());
                figures[1] = String.valueOf(numPicker1.getValue());
                figures[2] = String.valueOf(numPicker2.getValue());
                figures[3] = String.valueOf(numPicker3.getValue());

                String str = String.format("%s%s%s.%s%s",
                        figures[0], figures[1], figures[2], figures[3]);
                Float fig = Float.parseFloat(str);

                pickerTextView.setText(String.valueOf(fig));
            }
        });
    }

}