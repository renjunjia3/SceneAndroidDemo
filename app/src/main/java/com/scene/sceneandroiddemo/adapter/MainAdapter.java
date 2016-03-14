package com.scene.sceneandroiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scene.mylib.view.recyclerview.OnRecyclerViewItemClickListener;
import com.scene.sceneandroiddemo.R;
import com.scene.sceneandroiddemo.entity.TypeInfo;
import com.scene.sceneandroiddemo.viewholder.MainViewHoder;

import java.util.List;

/**
 * Created by scene on 16/01/26.
 */
public class MainAdapter extends RecyclerView.Adapter {
    private LayoutInflater mLayoutInflater;

    private Context mContext;
    private List<TypeInfo> dataList;

    private OnRecyclerViewItemClickListener listener;

    public MainAdapter(Context mContext, List<TypeInfo> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHoder(mLayoutInflater.inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MainViewHoder viewHoder = (MainViewHoder) holder;
        viewHoder.type.setText(dataList.get(position).getType());
        viewHoder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRecyclerViewItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }
}
