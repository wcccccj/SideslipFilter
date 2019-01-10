package com.example.study.android.androidscreening.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.study.android.androidscreening.R;
import com.example.study.android.androidscreening.adapter.RightSideslipLayAdapter;
import com.example.study.android.androidscreening.model.AttrList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性选择的布局及逻辑
 */
public class RightSideslipLay extends RelativeLayout {
    private Context mCtx;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private ImageView backBrand;
    private RelativeLayout mRelateLay;
    private RightSideslipLayAdapter slidLayFrameAdapter;
    private List<AttrList.Attr.Vals> ValsData;
    private String JsonStr = "{\"attr\": [{ \"isoPen\": true,\"single_check\": 1,\"key\": \"品牌\", \"vals\": [ { \"val\": \"雅格\"}, {\"val\": \"志高/Chigo\" }, {\"val\": \"格东方\" },{\"val\": \"Chigo\" }, {\"val\": \"格OW\" },{\"val\": \"志go\" }, {\"val\": \"格LLOW\" },{\"val\": \"志o\" }, {\"val\": \"LLOW\" }, {\"val\": \"众桥\"},{\"val\": \"超人/SID\" },{ \"val\": \"扬子342\" }, { \"val\": \"扬舒服\" }, { \"val\": \"扬子东方\"},{ \"val\": \"荣事达/Royalstar\"}]},{\"single_check\": 0,\"key\": \"是否进口\", \"vals\": [{ \"val\": \"国产\"},{ \"val\": \"进口\"}]}," +
            "{\"single_check\": 0,\"key\": \"灭蚊器类型\", \"vals\": [{ \"val\": \"光触媒灭蚊器\"}]}," +
            "{\"single_check\": 0,\"key\": \"个数\", \"vals\": [{\"val\": \"1个\"},{\"val\": \"1个\"},{\"val\": \"1个\"},{\"val\": \"1个\"},{\"val\": \"1个\"},{\"val\": \"2个\"},{\"val\": \"3个\"},{\"val\": \"4个\"},{\"val\": \"5个\"},{\"val\": \"5个以上\"},{\"val\": \"10个以上\"}]},{ \"single_check\": 0, \"key\": \"型号\",\"vals\": [{\"val\": \"SI23\" },{\"val\": \"SI23\" },{\"val\": \"SI343\" },{\"val\": \"SI563\" },{\"val\": \"Sgt23\" }]}]}";

    public RightSideslipLay(Context context) {
        super(context);
        mCtx = context;
        inflateView();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);
        selectList = (ListView) findViewById(R.id.selsectFrameLV);
        backBrand = (ImageView) findViewById(R.id.select_brand_back_im);
        resetBrand = (Button) findViewById(R.id.fram_reset_but);
        mRelateLay = (RelativeLayout) findViewById(R.id.select_frame_lay);
        okBrand = (Button) findViewById(R.id.fram_ok_but);
        //触发点击事件
        resetBrand.setOnClickListener(mOnClickListener);
        okBrand.setOnClickListener(mOnClickListener);
        backBrand.setOnClickListener(mOnClickListener);
        mRelateLay.setOnClickListener(mOnClickListener);
        setUpList();
    }


    // 此处为了将超过9个个数的类目进行缩略显示
    private List<AttrList.Attr> setUpBrandList(List<AttrList.Attr> mAttrList) {
        for (AttrList.Attr item : mAttrList) {
            item.setTempVals(getValsDatas(item.getVals()));
        }
        return mAttrList;
    }

    // 声明AttrList字符串实体类
    private AttrList attr;

    // 获取当前Sides列表数据
    private void setUpList() {
        attr = new Gson().fromJson(JsonStr.toString(), AttrList.class);
        if (slidLayFrameAdapter == null) {
            slidLayFrameAdapter = new RightSideslipLayAdapter(mCtx, setUpBrandList(attr.getAttr()));
            selectList.setAdapter(slidLayFrameAdapter);
        } else {
            // 当popupwindow消失时调用，更新第一页界面
            slidLayFrameAdapter.replaceAll(attr.getAttr());
        }
        slidLayFrameAdapter.setAttrCallBack(new RightSideslipLayAdapter.SelechDataCallBack() {
            @Override
            public void setupAttr(List<String> mSelectData, String key) {
                if (Boolean.valueOf(key)) {
                    Log.i("List111", key);
                }
            }
        });

        slidLayFrameAdapter.setMoreCallBack(new RightSideslipLayAdapter.SelechMoreCallBack() {

            @Override
            public void setupMore(List<AttrList.Attr.Vals> mSelectData, List<AttrList.Attr.Vals> ValsData, int position, String title) {
                getPopupWindow(mSelectData, ValsData, position, title);
                mDownMenu.setOnMeanCallBack(meanCallBack);
            }
        });

    }

    // 在第二个页面改变后，返回时第一个界面随之改变，使用的接口回调
    private RightSideslipChildLay.onMeanCallBack meanCallBack = new RightSideslipChildLay.onMeanCallBack() {
        @Override
        public void isDisMess(int position, List<AttrList.Attr.Vals> mTotalData, String str) {
            if (mTotalData != null) {
                ((AttrList.Attr) attr.getAttr().get(position)).setTempVals(getValsDatas(mTotalData));
                ((AttrList.Attr) attr.getAttr().get(position)).setShowStr(str);
                slidLayFrameAdapter.replaceAll(attr.getAttr());
            }

            dismissMenuPop();
        }
    };

    // 获取"查看更多"数据，超过8个时设为查看更多，当前为"品牌"类目
    private List<AttrList.Attr.Vals> getValsDatas(List<AttrList.Attr.Vals> mTotalData) {
        List<AttrList.Attr.Vals> mVals = new ArrayList<AttrList.Attr.Vals>();
        if (mTotalData != null && mTotalData.size() > 0) {
            for (int i = 0; i < mTotalData.size(); i++) {
                if (mVals.size() >= 8) {
                    AttrList.Attr.Vals valsAdd = new AttrList.Attr.Vals();
                    valsAdd.setV("查看更多 >");
                    mVals.add(valsAdd);
                    continue;
                } else {
                    mVals.add(mTotalData.get(i));
                }
            }
            mVals = mVals.size() >= 9 ? mVals.subList(0, 9) : mVals;
            return mVals;

        }
        return null;
    }

    // 点击事件的继承
    private OnClickListenerWrapper mOnClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.select_brand_back_im:
                case R.id.fram_ok_but:
                    menuCallBack.setupCloseMean();
                    break;

                case R.id.fram_reset_but:

                    break;
            }
        }
    };


    //设置第二屏内容
    /**
     * 关闭窗口
     */
    private void dismissMenuPop() {
        if (mMenuPop != null) {
            mMenuPop.dismiss();
            mMenuPop = null;
        }

    }

    /***
     * 获取PopupWindow实例
     * @param mSelectData 已选择的List
     * @param ValsData 所有元素List
     */
    private void getPopupWindow(List<AttrList.Attr.Vals> mSelectData, List<AttrList.Attr.Vals> ValsData, int pos, String title) {
        if (mMenuPop != null) {
            dismissMenuPop();
            return;
        } else {
            initPopuptWindow(mSelectData, ValsData, pos, title);
        }
    }

    /**
     * 创建PopupWindow
     */
    private PopupWindow mMenuPop;
    public RightSideslipChildLay mDownMenu;

    protected void initPopuptWindow(List<AttrList.Attr.Vals> mSelectData, List<AttrList.Attr.Vals> ValsData, int pos, String title) {
        mDownMenu = new RightSideslipChildLay(getContext(), ValsData, mSelectData, pos, title);
        if (mMenuPop == null) {
            mMenuPop = new PopupWindow(mDownMenu, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        }
        mMenuPop.setBackgroundDrawable(new BitmapDrawable());
        mMenuPop.setAnimationStyle(R.style.popupWindowAnimRight);
        mMenuPop.setFocusable(true);
        mMenuPop.showAtLocation(RightSideslipLay.this, Gravity.TOP, 100, UiUtils.getStatusBarHeight(mCtx));
        mMenuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                dismissMenuPop();
            }
        });
    }
    /**
     * 创建PopupWindow
     */


    // 外部接口暴露
    /**
     * 创建CloseMenuCallBack接口
     */
    private CloseMenuCallBack menuCallBack;

    public interface CloseMenuCallBack {
        void setupCloseMean();
    }

    public void setCloseMenuCallBack(CloseMenuCallBack menuCallBack) {
        this.menuCallBack = menuCallBack;
    }
}
