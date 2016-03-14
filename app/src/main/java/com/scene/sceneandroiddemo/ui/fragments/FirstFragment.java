package com.scene.sceneandroiddemo.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scene.common.common.assist.Check;
import com.scene.mylib.view.recyclerview.OnRecyclerViewItemClickListener;
import com.scene.sceneandroiddemo.BaseFragment;
import com.scene.sceneandroiddemo.R;
import com.scene.sceneandroiddemo.adapter.MainAdapter;
import com.scene.sceneandroiddemo.entity.TypeInfo;
import com.scene.sceneandroiddemo.ui.activity.LoadmoreActivity;
import com.scene.sceneandroiddemo.ui.activity.SelectAndCropImageActivity;
import com.scene.sceneandroiddemo.ui.activity.CustomRatingBarActivity;
import com.scene.sceneandroiddemo.ui.activity.WebViewDemo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class FirstFragment extends BaseFragment implements OnRecyclerViewItemClickListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainAdapter adapter;
    private List<TypeInfo> dataList;

    public static FirstFragment instance() {
        FirstFragment view = new FirstFragment();
        return view;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment1;
    }


    @Override
    public void init() {
        bindRecyclerView();
    }


    private void bindRecyclerView() {
        dataList = new ArrayList<>();
        adapter = new MainAdapter(getActivity(), dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        TypeInfo info1 = new TypeInfo(0, "RecyclerView下拉上滑");
        TypeInfo info2 = new TypeInfo(1, "图片选择与裁剪");
        TypeInfo info3 = new TypeInfo(2, "自定义评星条");
        TypeInfo info4 = new TypeInfo(3, "自定义评星条");
        dataList.add(info1);
        dataList.add(info2);
        dataList.add(info3);
        dataList.add(info4);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewItemClick(int position) {
        switch (position) {
            case 0://RecyclerView下拉上滑
                startActivity(new Intent(getActivity(), LoadmoreActivity.class));
                break;
            case 1://图片选择与裁剪
                startActivity(new Intent(getActivity(), SelectAndCropImageActivity.class));
                break;
            case 2://自定义评星条
                startActivity(new Intent(getActivity(), CustomRatingBarActivity.class));
                break;
            case 3://webView
                startActivity(new Intent(getActivity(), WebViewDemo.class));
                break;
        }
    }


}
