package com.example.sonota.ui.cal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sonota.R;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvTitle;
    private CalendarAdapter mCalendarAdapter;
    private GridView calendarGridView;
    private int index;
    private OnFragmentInteractionListener mListener;

    public CalendarFragment() {
        index = 0;
    }

    //フラグメントの引数付きコンストラクタはよろしくない　後でなおす必要あり
    public CalendarFragment(int index) {
        this.index = index;
    }

    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //画面の表示
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_calendar, container, false);
        tvTitle =rootview.findViewById(R.id.tvTitle);

        calendarGridView = rootview.findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalendarAdapter(getContext(),calendarGridView);
        calendarGridView.setAdapter(mCalendarAdapter);
        mCalendarAdapter.MoveMonthbyIndex(index);
        tvTitle.setText(mCalendarAdapter.getTitle());

        //カレンダー上のセルをクリックしたときのイベント
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1) {
//                    呼び出し元のアクティビティのonCheckedDateChangedを呼ぶ
                    mListener.onCheckedDateChanged(mCalendarAdapter.getDateByPosition(calendarGridView.getCheckedItemPosition()));
                }
                String PickDate = mCalendarAdapter.getDateString(calendarGridView.getCheckedItemPosition());

                //呼び出し元のアクティビティのonCalendarItemClickを呼ぶ
                mListener.onCalendarItemClick(PickDate);

                //選択したセルが今月の日付を示していないなら呼び出し元のアクティビティのonCheckedNotCurrentMonthを呼ぶ
                if(!mCalendarAdapter.isCurrentMonth(mCalendarAdapter.getDateByPosition(calendarGridView.getCheckedItemPosition()))){
                    if(position > mCalendarAdapter.getCount() / 2){
                        mListener.onCheckedNotCurrentMonth(true,false);
                    }else {
                        mListener.onCheckedNotCurrentMonth(false,false);
                    }
                }
            }
        });

    // Inflate the layout for this fragment
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        return rootview;
    }

    //mCalendarAdapterから取得したｄefaultPositionでsetItemCheckedTopositionを呼んで選択をtDefaultPositioｎに更新する
    public void setItemCheckedDefaultPosition(){
        setItemCheckedToposition(mCalendarAdapter.getDefaultPosition());
    }

    //上のメソッドのオーバーロード
    //int diffを受け取るとその月の１日の位置にdiffの値を足した位置(その月のdiff日目の位置)に設定するようになる
    public void setItemCheckedDefaultPosition(int diff){
        int position = mCalendarAdapter.getFirstDayPosition() + diff - 1;

        if(position < 0 || position >= mCalendarAdapter.getCount())
            return;

        setItemCheckedToposition(position);
    }

    //選択をカレンダーの一番右下(一番下の行が当月で埋まってる場合の最終日)に設定
    public void setItemCheckedLastView(){
        setItemCheckedToposition(mCalendarAdapter.getCount() - 1);
    }

    //選択をカレンダーの一番右上(一番上の行が当月で埋まってる場合の1日)に設定
    public void setItemCheckedFirstDay(){
        setItemCheckedToposition(mCalendarAdapter.getFirstDayPosition());
    }

    //位置を受け取ってその位置にカレンダー上の選択を変更する
    public void setItemCheckedToposition(int position){
        calendarGridView.setItemChecked(position,true);
        if(position != -1) {
            //呼び出し元のアクティビティのonCheckedDateChangedを呼ぶ -> 下のViewPagerを更新する
            mListener.onCheckedDateChanged(mCalendarAdapter.getDateByPosition(position));
        }
    }

    //選択されているセルの中身をDate型で返す
    public Date getCurrentDate(){
        int position = calendarGridView.getCheckedItemPosition();

        if(position < 0 || position >= mCalendarAdapter.getCount())
            return null;

        return mCalendarAdapter.getDateByPosition(calendarGridView.getCheckedItemPosition());
    }

    //カレンダー上の選択をdiffの分だけ移動する
    //主に下のViewPagerをスワイプした時に呼ぶ
    public void setItemCheckedToDiff(int diff){
        //移動した結果グリッドビューの範囲を超えると落ちたんで強制的に移動
        if(calendarGridView.getCheckedItemPosition() + diff >= mCalendarAdapter.getCount()){
            mListener.onCheckedNotCurrentMonth(true,true);
            return;
        }
        else if(calendarGridView.getCheckedItemPosition() + diff < 0){
            mListener.onCheckedNotCurrentMonth(false,true);
            return;
        }

        //カレンダー上の選択をdiffの分だけ移動する
        calendarGridView.setItemChecked(calendarGridView.getCheckedItemPosition() + diff,true);

        //呼び出し元のアクティビティのonCheckedNotCurrentMonthを呼び出す　－> ViewPagerのページを変える
        if(!mCalendarAdapter.isCurrentMonth(mCalendarAdapter.getDateByPosition(calendarGridView.getCheckedItemPosition()))){
            if(diff > 0){
                mListener.onCheckedNotCurrentMonth(true,false);
            }else {
                mListener.onCheckedNotCurrentMonth(false,false);
            }
        }
    }

    //デフォルトであるやつ
    //呼ばれてないし正体不明
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //表示されたときイベント
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            if(calendarGridView != null){
                setItemCheckedDefaultPosition();
            }
        }
    }

    //カレンダーの選択をクリア
    public void setCalendarCheckedClear(){
        calendarGridView.setItemChecked(-1,true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CalendarFragment.OnFragmentInteractionListener) {
            //呼び出し元アクティビティのコールバックをセット
            mListener = (CalendarFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //呼び出し元アクティビティのコールバック
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onCalendarItemClick(String pickdate);
        void onCheckedDateChanged(Date afterDate);
        void onCheckedNotCurrentMonth(boolean isNextMonth, boolean isOverCount);
    }


}
