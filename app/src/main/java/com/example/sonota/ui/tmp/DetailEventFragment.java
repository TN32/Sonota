package com.example.sonota.ui.tmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;

public class DetailEventFragment extends CustomFragment {

    EditText name1;
    Button bt_tmp_registration;

    private NumberPicker numPicker0, numPicker1, numPicker2, numPicker3;
    private TextView pickerTextView;
    private String[] figures = new String[5];

    private EditText et_cal_eventName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_tmp_detail_event, container, false);

        pickerTextView = root.findViewById(R.id.text_view);

        et_cal_eventName = root.findViewById(R.id.et_cal_eventName);

        numPicker0 = root.findViewById(R.id.np_tmp_startTime1);
        numPicker1 = root.findViewById(R.id.np_tmp_startTime2);
        numPicker2 = root.findViewById(R.id.np_tmp_endTime1);
        numPicker3 = root.findViewById(R.id.np_tmp_endTime2);



        numPicker0.setMaxValue(23);
        numPicker0.setMinValue(0);

        numPicker1.setMaxValue(59);
        numPicker1.setMinValue(0);

        numPicker2.setMaxValue(23);
        numPicker2.setMinValue(0);

        numPicker3.setMaxValue(59);
        numPicker3.setMinValue(0);


        bt_tmp_registration = root.findViewById(R.id.bt_tmp_registration);
        bt_tmp_registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
//                figures[0] = String.valueOf(numPicker0.getValue());
//                figures[1] = String.valueOf(numPicker1.getValue());
//                figures[2] = String.valueOf(numPicker2.getValue());
//                figures[3] = String.valueOf(numPicker3.getValue());
//
//                String str = String.format("%s%s%s.%s%s",
//                        figures[0], figures[1], figures[2], figures[3]);
//                Float fig = Float.parseFloat(str);

//                pickerTextView.setText(String.valueOf(fig));

                Toast.makeText(getContext(),"イベント名が変更されました!" +"("+ et_cal_eventName.getText().toString()+")" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("Event", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });


        Bundle args = getArguments();
        if(args != null){

            int selected = args.getInt("selected");

            name1 = (EditText)root.findViewById(R.id.et_cal_eventName);
            name1.setHint(args.getString("Name1"));

            String startTime = args.getString("starttime");
            String finishTime = args.getString("finishtime");

            String[] splitStartTime = startTime.split("時");

            String[] splitFinishTime = finishTime.split("時");

            numPicker0.setValue(Integer.valueOf(splitStartTime[0]));

            numPicker1.setValue(Integer.valueOf(splitStartTime[1].split("分")[0]));

            numPicker2.setValue(Integer.valueOf(splitFinishTime[0]));

            numPicker3.setValue(Integer.valueOf(splitFinishTime[1].split("分")[0]));

            bt_tmp_registration.setText("更新");
        }


        return root;
    }

}