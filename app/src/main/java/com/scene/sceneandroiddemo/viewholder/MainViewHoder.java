package com.scene.sceneandroiddemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scene.sceneandroiddemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by scene on 16/01/26.
 */
public class MainViewHoder extends RecyclerView.ViewHolder {
    @Bind(R.id.type)
    public TextView type;

    public MainViewHoder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
