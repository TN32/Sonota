package com.example.sonota.ui.ec;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonota.R;

import java.util.ArrayList;

public class PaymentListAdapter extends BaseAdapter {
    // contextはおまじないと思って記述してください（説明が難しいため）
    private Context context = null;

    // ArrayListの中に独自クラスのCustomDataClassを指定
    private ArrayList<PaymentClass> data = null;

    private int resource = 0;
    private ArrayList<String> dateTextList = new ArrayList();


    // コンストラクタ  MainActivityでアダプターを生成する箇所で呼ばれている
    public PaymentListAdapter(Context context, ArrayList<PaymentClass> data, int resource){
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
        PaymentClass data = (PaymentClass) getItem(position);

        // 再利用可能なビューが無かったら生成する
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        /**
         * ここから各項目に値を割り当てる処理
         */
        ImageView img = convertView.findViewById(R.id.im_Ec_PaymentIcon);
        if (data.isCregitPayment()== true){
            img.setImageResource(R.mipmap.ic_credit2_foreground);
        }else {
            img.setImageResource(R.mipmap.ic_money2_foreground);
        }

        // idがmainTextのTextViewに、指定されたデータのmainStringの値を格納している
        String[] dateText = data.getDate().split("_");
        ((TextView) convertView.findViewById(R.id.expenceList_TextView_Date_cell)).setText(dateText[0] + "年" + dateText[1] + "月" + dateText[2] + "日");
        dateTextList.add(data.getDate());


        ((TextView) convertView.findViewById(R.id.ExpenceList_TextView_Memo)).setText(data.getMemo());
        dateTextList.add(data.getMemo());


        TextView Amount = (TextView) convertView.findViewById(R.id.ExpenceList_TextView_Price);
        Amount.setText("￥" + data.getAmount());


        return convertView;
    }

    public String getCurrentMemo(int position){
        return data.get(position).getMemo();
    }
    public boolean isCregitPayment(int position){
        return data.get(position).isCregitPayment();
    }
    public int getAmount(int position){
        return data.get(position).getAmount();
    }
    public String getDate(int position){ return data.get(position).getDate(); }
}
