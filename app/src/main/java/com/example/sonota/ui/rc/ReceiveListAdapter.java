package com.example.sonota.ui.rc;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sonota.R;

import java.util.ArrayList;

public class ReceiveListAdapter extends BaseAdapter {
    // contextはおまじないと思って記述してください（説明が難しいため）
    private Context context = null;

    // ArrayListの中に独自クラスのCustomDataClassを指定
    private ArrayList<ReceiveClass> data = null;

    private int resource = 0;
    private ArrayList<String> dateTextList = new ArrayList();


    // コンストラクタ  MainActivityでアダプターを生成する箇所で呼ばれている
    public ReceiveListAdapter(Context context, ArrayList<ReceiveClass> data, int resource){
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
        ReceiveClass data = (ReceiveClass) getItem(position);

        // 再利用可能なビューが無かったら生成する
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        /**
         * ここから各項目に値を割り当てる処理
         */

        TextView title = (TextView) convertView.findViewById(R.id.tv_rc_receivetitle);
        title.setText(data.getTitle() + "  →  ￥");

        TextView receive = (TextView) convertView.findViewById(R.id.tv_rc_receive);
        receive.setText(data.getAmount() + "");

        TextView worktime = (TextView) convertView.findViewById(R.id.tv_rc_worktime);
        worktime.setText(data.getWorktime() + "分");

        TextView enduranc = (TextView) convertView.findViewById(R.id.tv_rc_endurance);
        enduranc.setText("￥" + data.getEndurance() + "/１ｈ");

        return convertView;
    }

    public String getCurrentDateText(int position){
        return dateTextList.get(position);
    }
}
