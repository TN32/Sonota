package com.example.sonota.ui.cal;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    private boolean enabled;

    private GestureDetectorCompat mDetector;
    private OnViewInteractionListener mListener;

    //スワイプ感度調節
    final int sensitivity = 250;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
        if (context instanceof CalendarFragment.OnFragmentInteractionListener) {
            //呼び出し元アクティビティのコールバックをセット
            mListener = (OnViewInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        mDetector.onTouchEvent(event);
        return enabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPagingEnabled() {
        return enabled;
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float dx = e1.getX() - e2.getX();
            float dy = e1.getY() - e2.getY();
            if (Math.abs(dy) > Math.abs(dx)) {
                if (dy > sensitivity) {
                    // 上方向フリック
                    mListener.animeup();
                } else if (dy < -sensitivity) {
                    // 下方向フリック
                    mListener.animedown();
                }
            }
            return true;
        }
    }

    public interface OnViewInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void animeup();
        void animedown();
    }

}