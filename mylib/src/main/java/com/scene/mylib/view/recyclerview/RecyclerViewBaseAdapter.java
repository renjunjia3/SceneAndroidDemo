package com.scene.mylib.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向RecyclerView基类adapter
 *
 * @param <ItemDataType> 数据实体类型
 * @author scene
 *         time 2015-04-10 12:33:43
 */
public abstract class RecyclerViewBaseAdapter<ItemDataType> extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ArrayList<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();

    public RecyclerViewBaseAdapter() {
    }

    /**
     * 动态增加一条数据
     *
     * @param itemDataType 数据实体类对象
     */
    public void append(ItemDataType itemDataType) {
        if (itemDataType != null) {
            mItemDataList.add(itemDataType);
            notifyDataSetChanged();
        }
    }

    /**
     * 动态增加一组数据集合
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void append(List<ItemDataType> itemDataTypes) {
        if (itemDataTypes.size() > 0) {
            for (ItemDataType itemDataType : itemDataTypes) {
                mItemDataList.add(itemDataType);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 替换全部数据
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void replace(List<ItemDataType> itemDataTypes) {
        mItemDataList.clear();
        if (itemDataTypes.size() > 0) {
            mItemDataList.addAll(itemDataTypes);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除一条数据集合
     *
     * @param position
     */
    public void remove(int position) {
        mItemDataList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 移除所有数据
     */
    public void removeAll() {
        mItemDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        showData(viewHolder, position, mItemDataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = createView(viewGroup, position);
        RecyclerView.ViewHolder holder = createViewHolder(view);
        return holder;
    }

    /**
     * 显示数据抽象函数
     *
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    public abstract void showData(RecyclerView.ViewHolder viewHolder, int i, List<ItemDataType> mItemDataList);

    /**
     * 加载item的view,直接返回加载的view即可
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     * @param position
     * @return item 的 view
     */
    public abstract View createView(ViewGroup viewGroup, int position);

    /**
     * 加载一个ViewHolder,为RecyclerViewHolderBase子类,直接返回子类的对象即可
     *
     * @param view item 的view
     * @return BaseViewHolder 基类ViewHolder
     */
    public abstract RecyclerView.ViewHolder createViewHolder(View view);
}