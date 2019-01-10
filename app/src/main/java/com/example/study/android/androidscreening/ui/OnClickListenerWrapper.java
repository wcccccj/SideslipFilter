package com.example.study.android.androidscreening.ui;

import android.view.View;

/**
 * 防止Button的频繁点击,多次执行点击事件
 * <p/>
 */
public abstract class OnClickListenerWrapper implements View.OnClickListener {

    private static long lastClickTime;

    protected abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        if (isFastDuplicateClick()) {
            return;
        }
        onSingleClick(v);
    }

    /**
     * 是否为重复的快速点击
     *
     * @return
     */
    protected boolean isFastDuplicateClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            lastClickTime = time;
            return true;
        }
        return false;
    }
}
