package fussen.yu.news.base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fussen on 2016/11/23.
 * <p>
 * 所有的listview 和gridview adapter的基类
 * <p>
 * 目前只支持显示两种不同的条目
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public static int TYPE_ONE = 0;//LstView显示第1种条目
    public static int TYPE_TWO = 1;//LstView显示第2种条目

    public static int TYPE_COUNT = 1;//listview显示几种条目

    private List<T> mData;

    public MyBaseAdapter(ArrayList<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 显示几种布局
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return getItemCount();
    }

    /**
     * 要显示哪种布局
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            if (getItemViewType(position) == TYPE_ONE) {
                holder = getHolder(position);
            } else {
                holder = getOtherHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        holder.setData(mData.get(position));
        return holder.convertView;
    }

    public abstract BaseHolder getHolder(int position);


    /**
     * 如果有其他种类的条目则必须复写此方法
     *
     * @param position
     * @return
     */
    public BaseHolder getOtherHolder(int position) {
        return null;
    }

    /**
     * 显示条目的哪种 种类
     *
     * @param position
     * @return
     */
    public int getItemType(int position) {
        return TYPE_ONE;
    }


    /**
     * 要显示几种条目
     *
     * @return
     */
    public int getItemCount() {
        return TYPE_COUNT;
    }

}
