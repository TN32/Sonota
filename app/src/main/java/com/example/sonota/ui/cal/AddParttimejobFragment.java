package com.example.sonota.ui.cal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.sonota.SonotaDBOpenHelper;

public class AddParttimejobFragment extends CustomFragment {

    EditText btime;
    NumberPicker numpicker1,numpicker2,numpicker3,numpicker4;

    public AddParttimejobFragment(){
        int i = 0;
    }

    private NumberPicker numPicker0, numPicker1, numPicker2, numPicker3;
    private TextView pickerTextView;
    TextView tv_cal_adddate;
    private String[] figures = new String[5];

    EditText bTime;

    Button bt_cal_registration;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_cal_add_parttimejob, container, false);

        super.onCreate(savedInstanceState);

        spinner = root.findViewById(R.id.sp_cal_partName);

        ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(),
                R.layout.custom_spnner,
                getResources().getStringArray(R.array.list)
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapter);

        pickerTextView = root.findViewById(R.id.text_view);

        numPicker0 = root.findViewById(R.id.np_cal_startTime1);
        numPicker1 = root.findViewById(R.id.np_cal_startTime2);
        numPicker2 = root.findViewById(R.id.np_cal_endTime1);
        numPicker3 = root.findViewById(R.id.np_cal_endTime2);

        numPicker0.setMaxValue(23);
        numPicker0.setMinValue(0);

        numPicker1.setMaxValue(59);
        numPicker1.setMinValue(0);

        numPicker2.setMaxValue(23);
        numPicker2.setMinValue(0);

        numPicker3.setMaxValue(59);
        numPicker3.setMinValue(0);

        bTime =root.findViewById(R.id.et_cal_braekTime);

        bt_cal_registration = root.findViewById(R.id.bt_cal_registration);
        bt_cal_registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String eventDate = tv_cal_adddate.getText().toString();
                int byteaheadCode = (int)spinner.getAdapter().getItemId(spinner.getSelectedItemPosition());

                int btime;
                if(bTime.getText().toString() == null || bTime.getText().toString().equals("")){
                    btime = 0;
                }
                else {
                    btime = Integer.valueOf(bTime.getText().toString());
                }


                figures[0] = String.valueOf(numPicker0.getValue());
                figures[1] = String.valueOf(numPicker1.getValue());
                figures[2] = String.valueOf(numPicker2.getValue());
                figures[3] = String.valueOf(numPicker3.getValue());

                if (helper == null){
                    helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                insertData(db, byteaheadCode, eventDate, figures[0] + "_" + figures[1], figures[2] + "_" + figures[3],btime);

                Toast.makeText(getContext(),  bt_cal_registration.getText() + "が完了しました!",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("CalenderContent", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if(args != null){
            String addDate = args.getString("addDate");
            tv_cal_adddate = root.findViewById(R.id.tv_cal_adddate);
            tv_cal_adddate.setText(addDate);
            bt_cal_registration.setText("登録");
        }

        return root;
    }

    private void insertData(SQLiteDatabase db, int byteahead_code, String date, String stime, String etime,int btime){
        ContentValues values = new ContentValues();
        values.put("byteahead_code", byteahead_code);
        values.put("shift_date", date);
        values.put("shift_stime", stime);
        values.put("shift_etime", etime);
        values.put("shift_btime", btime);

        db.insert("t_shift",null, values);
    }
}