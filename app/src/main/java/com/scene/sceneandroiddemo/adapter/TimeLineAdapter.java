package com.scene.sceneandroiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scene.sceneandroiddemo.R;
import com.scene.sceneandroiddemo.viewholder.TimeLineViewHolder;
import com.scene.timeline.TimelineView;

import java.util.List;

/**
 * Created by scene on 16/02/25.
 */
public class TimeLineAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater mlayoutInflater;

    public TimeLineAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mlayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLineViewHolder(mlayoutInflater.inflate(R.layout.item_time_line, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeLineViewHolder viewHolder = (TimeLineViewHolder) holder;
        viewHolder.name.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
