package com.example.study.android.androidscreening.ui;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.study.android.androidscreening.R;


public class ScreeningActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private RelativeLayout navigationView;
    private RightSideslipLay menuHeaderView;
    private TextView mFrameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        initView();
        initEvent();

    }


    // 初始化元素
    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (RelativeLayout) findViewById(R.id.nav_view);
        mFrameTv = (TextView) findViewById(R.id.screenTv);

        //设置第一屏内容
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        menuHeaderView = new RightSideslipLay(ScreeningActivity.this);
        navigationView.addView(menuHeaderView);
    }

    // 初始化事件
    private void initEvent() {
        mFrameTv.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                openMenu();
            }
        });
        menuHeaderView.setCloseMenuCallBack(new RightSideslipLay.CloseMenuCallBack() {
            // 重写内部方法
            @Override
            public void setupCloseMean() {
                closeMenu();
            }
        });

    }

    // GravityCompat.START : 关闭左边菜单
    // GravityCompat.END ： 右边菜单是否打开
    // 返回值true or false
    public void closeMenu() {
        drawer.closeDrawer(GravityCompat.END);
    }

    public void openMenu() {
        drawer.openDrawer(GravityCompat.END);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawers();
        else
            super.onBackPressed();
    }
}
