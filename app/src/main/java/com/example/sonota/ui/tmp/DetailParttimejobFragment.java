package com.example.sonota.ui.tmp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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
import android.database.sqlite.SQLiteDatabase;

import com.example.sonota.CustomFragment;
import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;

public class DetailParttimejobFragment extends CustomFragment {

    EditText name1;
    NumberPicker numpicker1,numpicker2,numpicker3,numpicker4;

    public DetailParttimejobFragment(){
        int i = 0;
    }


    private NumberPicker numPicker0, numPicker1, numPicker2, numPicker3;
    private TextView pickerTextView;
    private String[] figures = new String[6];

    private Button bt_tmp_registration;

    Spinner sp_tmp_partName;

    private EditText et_tmp_braekTime;
    boolean isNewItem = true;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_tmp_detail_parttimejob, container, false);

        super.onCreate(savedInstanceState);

        et_tmp_braekTime = root.findViewById(R.id.et_tmp_braekTime);

        if (helper == null) {
            helper = new SonotaDBOpenHelper(getActivity().getApplicationContext());
        }

        if (db == null) {
            db = helper.getWritableDatabase();
        }

        //  引数distinctには、trueを指定すると検索結果から重複する行を削除します。
        //  引数tableには、テーブル名を指定します。
        //  引数columnsには、検索結果に含める列名を指定します。nullを指定すると全列の値が含まれます。
        //  引数selectionには、検索条件を指定します。
        //  引数selectionArgsには、検索条件のパラメータ（？で指定）に置き換わる値を指定します。
        //  引数groupByには、groupBy句を指定します。
        //  引数havingには、having句を指定します。
        //  引数orderByには、orderBy句を指定します。
        //  引数limitには、検索結果の上限レコードを数を指定します
        Cursor cursor = db.query(
                "t_byteahead",
                new String[]{"byteahead_code","byteahead_name","byteahead_hwage"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        ArrayList<String> listData = new ArrayList<>();
        final ArrayList<String> listId = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++) {
            String data = cursor.getString(1) + " : 時給 " + cursor.getInt(2) + "円";
            listData.add(data);
            listId.add(String.valueOf(cursor.getInt(0)));
            cursor.moveToNext();
        }

        spinner = root.findViewById(R.id.sp_tmp_partName);

        ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(),
                R.layout.custom_spnner,
                listData
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

        //データベースの更新

        //データベースの生成
 //       SonotaDBOpenHelper hlp = new SonotaDBOpenHelper(this);
 //       final SQLiteDatabase db = hlp.getReadableDatabase();

        bt_tmp_registration = root.findViewById(R.id.bt_tmp_registration);
        bt_tmp_registration.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(spinner.getSelectedItem() == null){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("登録できませんでした！")
                            .setMessage("アルバイト先が登録されていません。")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                int byteaheadCode = Integer.valueOf(listId.get((int)spinner.getAdapter().getItemId(spinner.getSelectedItemPosition())));
                int breakTime;

                if(et_tmp_braekTime.getText().toString() == null || et_tmp_braekTime.getText().toString().equals("")){
                    breakTime = 0;

                }
                else {
                    breakTime = Integer.valueOf(et_tmp_braekTime.getText().toString());
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

                if (isNewItem){
                    insertData(db, byteaheadCode,figures[0] + "_" + figures[1], figures[2] + "_" + figures[3],breakTime);
                }
                else {
                    updata(db, byteaheadCode,figures[0] + "_" + figures[1], figures[2] + "_" + figures[3],breakTime);
                }

                Toast.makeText(getContext(),"アルバイトテンプレートを登録しました!" ,Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack("Parttimejob", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        Bundle args = getArguments();
        if(args != null){

            selected = args.getInt("selected");

            String startTime = args.getString("starttime");
            String finishTime = args.getString("finishtime");

            String[] splitStartTime = startTime.split("_");

            String[] splitFinishTime = finishTime.split("_");

            numPicker0.setValue(Integer.valueOf(splitStartTime[0]));

            numPicker1.setValue(Integer.valueOf(splitStartTime[1]));

            numPicker2.setValue(Integer.valueOf(splitFinishTime[0]));

            numPicker3.setValue(Integer.valueOf(splitFinishTime[1]));

            bt_tmp_registration.setText("更新");
            et_tmp_braekTime.setText(String.valueOf(args.getInt("bteaktime")));
        }

        return root;
    }

    @Override
    public boolean onBackPressed(){
        final String[] items = { "破棄する", "このページに留まる"};
        new AlertDialog.Builder(getActivity()).setTitle("登録内容を破棄しますか？").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //                             item_which pressed
                switch (which) {
                    case 0:
                        Toast.makeText(getContext(),"登録内容が破棄されました。" ,Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack("Parttimejob", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        break;
                    case 1:
                        break;
                }

            }
        }).show();

        return true;
    }

    int selected;

    private void updata(SQLiteDatabase db, int byteahead_code, String stime, String etime,int btime){
        ContentValues values = new ContentValues();
        values.put("byteahead_code", byteahead_code);
        values.put("Parttimejobtemplate_stime", stime);
        values.put("Parttimejobtemplate_etime", etime);
        values.put("Parttimejobtemplate_btime", btime);

        String[] whereArgs = {String.valueOf(selected)};

        db.update("t_Parttimejobtemplate",values, "Parttimejobtemplate_code=?",whereArgs);
    }

    private void insertData(SQLiteDatabase db, int byteahead_code, String stime, String etime,int btime){
        ContentValues values = new ContentValues();
        values.put("byteahead_code", byteahead_code);
        values.put("Parttimejobtemplate_stime", stime);
        values.put("Parttimejobtemplate_etime", etime);
        values.put("Parttimejobtemplate_btime", btime);

        db.insert("t_Parttimejobtemplate",null, values);
    }
}