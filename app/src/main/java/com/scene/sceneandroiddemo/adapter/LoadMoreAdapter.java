package com.scene.sceneandroiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scene.mylib.view.recyclerview.OnRecyclerViewItemClickListener;
import com.scene.mylib.view.recyclerview.RecyclerViewUtils;
import com.scene.sceneandroiddemo.R;
import com.scene.sceneandroiddemo.viewholder.MainViewHoder;

import java.util.List;

/**
 * Created by Administrator on 16/01/26.
 */
public class LoadMoreAdapter extends RecyclerView.Adapter {
    private LayoutInflater mLayoutInflater;

    private Context mContext;
    private List<String> dataList;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener listener;

    public LoadMoreAdapter(Context mContext, List<String> dataList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHoder(mLayoutInflater.inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MainViewHoder viewHoder = (MainViewHoder) holder;
        viewHoder.type.setText(dataList.get(position));
        viewHoder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  处理如果有headerview或者footerview时候的点击事件
                 *  需要重新计算item的位置
                 */
                if (listener != null) {
                    listener.onRecyclerViewItemClick(RecyclerViewUtils.getAdapterPosition(recyclerView, viewHoder));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

}
