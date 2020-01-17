package com.example.sonota.ui.cal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sonota.R;

import java.util.ArrayList;

public class ScheduleListAdapter extends BaseAdapter {

    // contextはおまじないと思って記述してください（説明が難しいため）
    private Context context = null;

    // ArrayListの中に独自クラスのCustomDataClassを指定
    private ArrayList<ScheduleListClass> data = null;

    private int resource = 0;


    // コンストラクタ  MainActivityでアダプターを生成する箇所で呼ばれている
    public ScheduleListAdapter(Context context, ArrayList<ScheduleListClass> data, int resource){
        this.context = context;
        this.data = data;
        this.resource = resource;
    }


    /**
     * データの個数を返すメソッド
     * ※このメソッドは必ず記述すること
     */
    public int getCount() {
        return data.size();
    }

    /**
     * 指定された順番にある項目を返すメソッド
     * ※このメソッドは必ず記述すること
     */
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 指定された順番にある項目の識別idを返すメソッド
     * ※このメソッドは必ず記述すること
     */
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    /**
     * リスト項目を表示するためのメソッド
     * 自作アダプターを作成するにあたって一番重要
     * 実際にユーザが呼ぶ箇所ではなく、リストを生成するために自動で呼ばれる
     * ※このメソッドは必ず記述すること
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        // 指定された位置のデータを取得
        ScheduleListClass data = (ScheduleListClass) getItem(position);

        // 再利用可能なビューが無かったら生成する
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        /**
         * ここから各項目に値を割り当てる処理
         */
        // idがmainTextのTextViewに、指定されたデータのmainStringの値を格納している
        ((TextView) convertView.findViewById(R.id.tv_cal_schedule_title)).setText(data.getTitle());
        // こっちの書き方のほうがいつもの書き方なのでわかりやすいかも？
        TextView sub = (TextView) convertView.findViewById(R.id.tv_cal_schedule_starttime);
        String[] sTime = data.getStarttime().split("_");
        sub.setText(sTime[0] + "時" + sTime[1] + "分");
        // idがdescriptionのTextViewに、指定されたデータのdescriptionの値を格納している
        String[] eTime = data.getFinishtime().split("_");
        ((TextView) convertView.findViewById(R.id.tv_cal_schedule_endtime)).setText(eTime[0] + "時" + eTime[1] + "分");

        String[] split = data.getTitle().split("]");
        if(split[0].equals("[アルバイト")) {
            ((TextView) convertView.findViewById(R.id.tv_cal_parttimejob_breaktime)).setText("休憩時間:" + data.getBreaktime() + "分");
        } else {
            ((TextView) convertView.findViewById(R.id.tv_cal_parttimejob_breaktime)).setText("");
        }
        return convertView;
    }
    public boolean isPtj(int position){
        String[] split = data.get(position).getTitle().split("]");
        if(split[0].equals("[アルバイト")) {
            return true;
        }
        return false;
    }
}
