package com.example.study.android.androidscreening.ui;

import android.content.Context;

/**
 */

public class UiUtils {

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static  int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = context.getResources().getDimensionPixelSize(resourceId);
        return height;
    }
}
