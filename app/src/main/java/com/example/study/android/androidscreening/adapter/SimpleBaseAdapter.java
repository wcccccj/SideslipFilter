package com.example.study.android.androidscreening.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：描述:通用、简洁的Adapter。实现了BaseAdapter必须要重写getCount(), getItem(), getItemId(), getView()这几个方法，以及ViewHolder的通用实现。
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> data;

    public SimpleBaseAdapter(Context context) {
        this(context, null);
    }

    public SimpleBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int getItemResource();

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param holder
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void addItem(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    //每一个item项，返回一次界面
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            // 没有缓存， 重新生成convertView
            convertView = View.inflate(context, getItemResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
//               AutoUtils.autoSize(convertView);
        } else {
            // 有缓存时调用getTag获取缓存
            holder = (ViewHolder) convertView.getTag();
        }
        return getItemView(position, convertView, holder);
    }

    public List<T> getData() {
        return data;
    }

    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }


}
