package com.example.study.android.androidscreening.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by songyan on 2017/2/5.
 */

public class AttrList implements Serializable {

    private List<Attr> attr;

    public List getAttr() {
        return attr;
    }

    public void setAttr(List attr) {
        this.attr = attr;
    }

    public static class Attr implements Serializable {
        //属性值的title
        private String key;
        //属性值的title对应的数据
        private List<Vals> vals;
        //设置选中的数据
        private List<Vals> SelectVals;
        // 设置临时属性值的title临时的数据
        private List<Vals> tempVals;
        //状态是否打开
        private boolean isoPen = false;
        private int single_check = 0;
        private String showStr = "";


        public boolean isoPen() {
            return isoPen;
        }

        public void setIsoPen(boolean isoPen) {
            this.isoPen = isoPen;
        }

        public List<Vals> getSelectVals() {
            return SelectVals;
        }

        public void setSelectVals(List<Vals> selectVals) {
            SelectVals = selectVals;
        }

        public List<Vals> getTempVals() {
            return tempVals;
        }

        public void setTempVals(List<Vals> tempVals) {
            this.tempVals = tempVals;
        }

        public String getShowStr() {
            return showStr;
        }

        public void setShowStr(String showStr) {
            this.showStr = showStr;
        }

        public List<Vals> getVals() {
            return vals;
        }

        public void setVals(List<Vals> vals) {
            this.vals = vals;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getSingle_check() {
            return single_check;
        }

        public void setSingle_check(int single_check) {
            this.single_check = single_check;
        }

        public static class Vals implements Serializable {
            private String val;
            private boolean isChick;

            public boolean isChick() {
                return isChick;
            }

            public void setChick(boolean chick) {
                isChick = chick;
            }

            public String getV() {
                return val;
            }

            public void setV(String v) {
                this.val = v;
            }
        }
    }
}
