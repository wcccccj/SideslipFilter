package com.example.study.android.androidscreening.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.study.android.androidscreening.R;
import com.example.study.android.androidscreening.model.AttrList;
import com.example.study.android.androidscreening.ui.AutoMeasureHeightGridView;
import com.example.study.android.androidscreening.ui.OnClickListenerWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class RightSideslipLayAdapter extends SimpleBaseAdapter<AttrList.Attr> {

    public RightSideslipLayAdapter(Context context, List<AttrList.Attr> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_right_sideslip_lay;
    }


    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        // 每项item元素初始化
        // 当前筛选项的类目名
        TextView itemFrameTitleTv = holder.getView(R.id.item_frameTv);
        // 已选择的选项
        TextView itemFrameSelectTv = holder.getView(R.id.item_selectTv);
        // 获取整个Item控件
        LinearLayout layoutItem = holder.getView(R.id.item_select_lay);
        // 可根据item多少自适应高度
        AutoMeasureHeightGridView itemFrameGv = holder.getView(R.id.item_selectGv);
        itemFrameGv.setVisibility(View.VISIBLE);
        // 获取适配器中的数据
        AttrList.Attr mAttr = getData().get(position);
        // 为item元素的值进行初始化
        itemFrameTitleTv.setText(mAttr.getKey());
        itemFrameSelectTv.setText(mAttr.getShowStr());
        // 如果数据不为空，则弹出
        if (mAttr.getVals() != null) {
            convertView.setVisibility(View.VISIBLE);
            // 如果数据中有打开的参数则自动打开
            if (mAttr.isoPen()) {
                itemFrameSelectTv.setTag(itemFrameGv);
                itemFrameSelectTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_prodcatelist, 0);
                fillLv2CateViews(mAttr, mAttr.getTempVals(), itemFrameGv, position);
                layoutItem.setTag(itemFrameGv);
            } else {
                fillLv2CateViews(mAttr, mAttr.getTempVals().subList(0, 0), itemFrameGv, position);
                itemFrameSelectTv.setText(mAttr.getShowStr());
                itemFrameSelectTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_prodcatelist, 0);
                layoutItem.setTag(itemFrameGv);
                itemFrameSelectTv.setVisibility(View.VISIBLE);
            }
            layoutItem.setOnClickListener(onClickListener);
        } else {
            convertView.setVisibility(View.GONE);
        }
        itemFrameGv.setTag(position);
        return convertView;
    }

    /**
     * 为第二页填充数据
     * @param mAttr 获取集合中当前筛选项所有信息
     * @param list 第一页ListView展示元素
     * @param childLvGV 第一页缩略筛选项GridView
     */
    private void fillLv2CateViews(final AttrList.Attr mAttr, List<AttrList.Attr.Vals> list,
                                  AutoMeasureHeightGridView childLvGV, final int pos) {
        final RightSideslipLayChildAdapter mChildAdapter;
        if (mAttr.getSelectVals() == null) {
            mAttr.setSelectVals(new ArrayList<AttrList.Attr.Vals>());
        }
        if (childLvGV.getAdapter() == null) {
            mChildAdapter = new RightSideslipLayChildAdapter(context, list, mAttr.getSelectVals());
            childLvGV.setAdapter(mChildAdapter);
        } else {
            mChildAdapter = (RightSideslipLayChildAdapter) childLvGV.getAdapter();
            mAttr.setSelectVals(mAttr.getSelectVals());
            mChildAdapter.setSeachData(mAttr.getSelectVals());
            mChildAdapter.replaceAll(list);
        }

        // 点击 GV 项时触发
        mChildAdapter.setSlidLayFrameChildCallBack(new RightSideslipLayChildAdapter.SlidLayFrameChildCallBack() {
            @Override
            public void CallBackSelectData( List<AttrList.Attr.Vals> seachData) {
                mAttr.setShowStr(setupSelectStr(seachData));
                mAttr.setSelectVals(seachData);
                notifyDataSetChanged();
                selechDataCallBack.setupAttr(setupSelectDataStr(seachData), mAttr.getKey());

            }
        });
        // 当点击查看更多时触发
        mChildAdapter.setShowPopCallBack(new RightSideslipLayChildAdapter.ShowPopCallBack() {
            @Override
            public void setupShowPopCallBack(List<AttrList.Attr.Vals> seachData) {
                mAttr.setSelectVals(seachData);
                mAttr.setShowStr(setupSelectStr(seachData));
                // 打开 查看更多 列表
                mSelechMoreCallBack.setupMore(seachData, mAttr.getVals(), pos, mAttr.getKey());
            }
        });

    }

    private String setupSelectStr(List<AttrList.Attr.Vals> data) {
        StringBuilder builder = new StringBuilder();
        if (data != null) {
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

    private List<String> setupSelectDataStr(List<AttrList.Attr.Vals> data) {
        List<String> mSelectData = new ArrayList<String>();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                mSelectData.add(data.get(i).getV());
            }
            return mSelectData;
        } else {
            return null;
        }

    }

    // 监听点击事件
    OnClickListenerWrapper onClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            int id = v.getId();
            if (id == R.id.item_select_lay) {
                AutoMeasureHeightGridView childLv3GV = (AutoMeasureHeightGridView) v.getTag();
                int pos = (int) childLv3GV.getTag();
                AttrList.Attr itemdata = data.get(pos);
                boolean isSelect = !itemdata.isoPen();
                // 再将当前选择CB的实际状态
                itemdata.setIsoPen(isSelect);
                notifyDataSetChanged();
            }
        }
    };

    // 暴露接口给RightSideslipLay作 选择数据 回调函数
    public interface SelechDataCallBack {
        void setupAttr(List<String> mSelectData, String key);
    }

    public SelechDataCallBack selechDataCallBack;

    public void setAttrCallBack(SelechDataCallBack m) {
        selechDataCallBack = m;

    }

    // 暴露接口给RightSideslipLay作 选择更多 回调函数
    public interface SelechMoreCallBack {
        /**
         * 定义抽象方法
         * @param da 已选择元素
         * @param ValsData 当前筛选项所有元素
         * @param pos 当前筛选类目定位
         */
        void setupMore(List<AttrList.Attr.Vals> da, List<AttrList.Attr.Vals> ValsData, int pos, String title);
    }

    public SelechMoreCallBack mSelechMoreCallBack;

    public void setMoreCallBack(SelechMoreCallBack m) {
        mSelechMoreCallBack = m;

    }
}
