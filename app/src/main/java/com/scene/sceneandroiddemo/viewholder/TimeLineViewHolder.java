package com.scene.sceneandroiddemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scene.sceneandroiddemo.R;
import com.scene.timeline.TimelineView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by scene on 16/02/25.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.time_marker)
    public TimelineView time_marker;
    @Bind(R.id.tx_name)
    public TextView name;

    public TimeLineViewHolder(View itemView, int timeLineType) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        time_marker.initLine(timeLineType);
    }
}
