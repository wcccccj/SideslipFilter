package com.example.study.android.androidscreening.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.study.android.androidscreening.R;
import com.example.study.android.androidscreening.adapter.ScreeningListAdapter;
import com.example.study.android.androidscreening.model.AttrList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 *
 * 右方滑出的子控件
 *
 */

public class RightSideslipChildLay extends FrameLayout {
    private List<AttrList.Attr.Vals> mVals_data = new ArrayList<AttrList.Attr.Vals>();
    private ListView mBrandList;
    private ScreeningListAdapter mAdapter;
    private Context mCtx;
    private List<AttrList.Attr.Vals> selectBrandData;
    private ImageView meunBackIm;
    private TextView meunOkTv;
    private int position;

    /**
     * 构造函数
     * @param context 获取Context对象
     * @param mVals_data 缩略过的筛选项集合
     * @param Fristlist 已选择的筛选项集合
     */
    public RightSideslipChildLay(Context context, List<AttrList.Attr.Vals> mVals_data,
                                 List<AttrList.Attr.Vals> Fristlist, int pos) {
        super(context);
        mCtx = context;
        this.mVals_data = mVals_data;
        this.position = pos;
        selectBrandData = new ArrayList<AttrList.Attr.Vals>();
        initView(mVals_data, Fristlist);
    }

    //初始化view
    private void initView(List<AttrList.Attr.Vals> datas, List<AttrList.Attr.Vals> Fristlist) {
        View.inflate(getContext(), R.layout.include_right_sideslip_child_layout, this);
        mBrandList = (ListView) findViewById(R.id.select_brand_list);
        meunBackIm = (ImageView) findViewById(R.id.select_brand_back_im);
        meunOkTv = (TextView) findViewById(R.id.select_brand_ok_tv);
        meunBackIm.setOnClickListener(ClickListener);
        meunOkTv.setOnClickListener(ClickListener);
        setupList(datas, Fristlist);

    }

    //设置默认选中的CheckBox的状态
    private void setupList(List<AttrList.Attr.Vals> mBrand_data, List<AttrList.Attr.Vals> Fristlist) {
        if (mBrand_data != null && mBrand_data.size() > 0) {
            // 遍历当前筛选元素
            for (int i = 0; i < mBrand_data.size(); i++) {
                if (Fristlist != null && Fristlist.size() > 0) {
                    // 设置已选择的元素状态
                    for (int j = 0; j < Fristlist.size(); j++) {
                        if (mBrand_data.get(i).getV().equals(Fristlist.get(j).getV())) {
                            mBrand_data.get(i).setChick(Fristlist.get(j).isChick());
                        }
                    }
                } else {
                    mBrand_data.get(i).setChick(false);
                }

            }
            selectBrandData.clear();
            setupSelectData(mBrand_data);
            if (mAdapter == null) {
                mAdapter = new ScreeningListAdapter(mCtx, mBrand_data);
            } else {
                mAdapter.clear();
                mAdapter.addAll(mBrand_data);
            }
            mBrandList.setAdapter(mAdapter);

            mAdapter.setClickBack(new ScreeningListAdapter.ClickBack() {
                @Override
                public void setupClick() {
                    setupSelectData(mAdapter.getData());
                }
            });

        }

    }

    //设置选中的CheckBox list
    private void setupSelectData(List<AttrList.Attr.Vals> mList) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChick()) {
                selectBrandData.add(mList.get(i));
            } else {
                selectBrandData.remove(mList.get(i));

            }
        }
        selectBrandData = removeDuplicate(selectBrandData);
    }

    // 触发点击事件
    private OnClickListenerWrapper ClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                // 返回键功能
                case R.id.select_brand_back_im:
                    meanCallBack.isDisMess(position, null, "");
                    break;
                // 确认键功能
                case R.id.select_brand_ok_tv:
                    meanCallBack.isDisMess(position, mVals_data, setUpValsStrs(removeDuplicate(selectBrandData)));
                    break;

            }
        }
    };

    // 去除重复数据
    public List<AttrList.Attr.Vals> removeDuplicate(List<AttrList.Attr.Vals> list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    // 获取PopupWindow所选择的Item
    private String setUpValsStrs(List<AttrList.Attr.Vals> data) {
        StringBuilder builder = new StringBuilder();
        if (data != null) {
            // 循环所选项，只有一条与最后一条的情况不添加逗号
            for (int i = 0; i < data.size(); i++) {
                if (data.size() == 1) {
                    builder.append(data.get(i).getV());
                } else {
                    if (i == data.size() - 1) {
                        builder.append(data.get(i).getV());
                    } else {
                        builder.append(data.get(i).getV() + ",");
                    }
                }
            }
            return new String(builder);
        } else {
            return "";
        }

    }

    // 暴露给外部类使用
    public interface onMeanCallBack {
        /**
         * popupWindow回调第一页
         * @param position 回调时进行集合定位
         * @param data 该筛选项所有元素
         * @param str 已选择的内容字符串，用逗号隔开
         */
        void isDisMess(int position, List<AttrList.Attr.Vals> data, String str);
    }

    private onMeanCallBack meanCallBack;

    public void setOnMeanCallBack(onMeanCallBack m) {
        meanCallBack = m;
    }


}
