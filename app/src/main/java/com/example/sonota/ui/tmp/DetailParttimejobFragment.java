package com.example.sonota.ui.tmp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;

public class DetailParttimejobFragment extends CustomFragment {

    EditText name1;
    NumberPicker numpicker1,numpicker2,numpicker3,numpicker4;

    public DetailParttimejobFragment(){
        int i = 0;
    }


    private NumberPicker numPicker0, numPicker1, numPicker2, numPicker3;
    private TextView pickerTextView;
    private String[] figures = new String[5];

    Button bt_tmp_registration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_tmp_detail_parttimejob, container, false);

        super.onCreate(savedInstanceState);

        Spinner spinner = root.findViewById(R.id.sp_tmp_partName);

        ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(),
                R.layout.custom_spnner,
                getResources().getStringArray(R.array.list)
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapter);

        pickerTextView = root.findViewById(R.id.text_view);

        numPicker0 = root.findViewById(R.id.np_tmp_startTime1);
        numPicker1 = root.findViewById(R.id.np_tmp_startTime2);
        numPicker2 = root.findViewById(R.id.np_tmp_endTime1);
        numPicker3 = root.findViewById(R.id.np_tmp_endTime2);

        Button pickerButton = root.findViewById(R.id.bt_tmp_registration);

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

                Toast.makeText(getContext(),  bt_tmp_registration.getText() + "が完了しました!",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("Parttimejob", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });



        Bundle args = getArguments();
        if(args != null){

            int selected = args.getInt("selected");

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