package example.fussen.baselibrary.base.adapter;

import android.view.View;

/**
 * Created by Fussen on 2016/11/23.
 */

public abstract class BaseHolder<T> {

    public View convertView;
    public T mData;

    public BaseHolder() {
        convertView = initView();
        convertView.setTag(this);
    }

    public void setData(T data) {
        this.mData = data;
        if (mData != null) {
            refreshView();
        }
    }
    //初始化布局
    public abstract View initView();

    //填充数据
    public abstract void refreshView();
}
