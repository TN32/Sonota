package com.example.sonota.ui.tmp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sonota.R;

import java.util.ArrayList;

public class ParttimeJobListAdapter extends BaseAdapter {

    // contextはおまじないと思って記述してください（説明が難しいため）
    private Context context = null;

    // ArrayListの中に独自クラスのCustomDataClassを指定
    private ArrayList<ParttimeJobListClass> data = null;

    private int resource = 0;


    // コンストラクタ  MainActivityでアダプターを生成する箇所で呼ばれている
    public ParttimeJobListAdapter(Context context, ArrayList<ParttimeJobListClass> data, int resource){
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
        ParttimeJobListClass data = (ParttimeJobListClass) getItem(position);

        // 再利用可能なビューが無かったら生成する
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        /**
         * ここから各項目に値を割り当てる処理
         */
        // 画像割り当て
        ((TextView) convertView.findViewById(R.id.tv_cal_event_tille)).setText(data.getTitle());
        // idがmainTextのTextViewに、指定されたデータのmainStringの値を格納している
        ((TextView) convertView.findViewById(R.id.tv_tem_event_start)).setText(data.getStarttime());
        // こっちの書き方のほうがいつもの書き方なのでわかりやすいかも？
        ((TextView) convertView.findViewById(R.id.tv_tem_event_filsh)).setText(data.getFinishtime());
        // idがdescriptionのTextViewに、指定されたデータのdescriptionの値を格納している
        ((TextView) convertView.findViewById(R.id.tv_tem_parttimejob_breaktime)).setText( data.getBreaktime() + "分");

        return convertView;
    }

    public String getCurrentStartTime(int position){
        return data.get(position).getStarttime();
    }

    public String getCurrentFinishTime(int position){
        return data.get(position).getFinishtime();
    }
}


