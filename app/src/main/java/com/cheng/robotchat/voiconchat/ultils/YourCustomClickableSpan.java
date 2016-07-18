package com.cheng.robotchat.voiconchat.ultils;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by Welcome on 5/28/2016.
 */
public class YourCustomClickableSpan extends ClickableSpan {
    private String url;
    private OnClickListener mListener;
    public YourCustomClickableSpan(String url, OnClickListener mListener) {
        this.url = url;
        this.mListener = mListener;
    }
    @Override
    public void onClick(View widget) {
        if (mListener != null) mListener.onClick(url);
    }
    public interface OnClickListener {
        void onClick(String url);
    }
}
