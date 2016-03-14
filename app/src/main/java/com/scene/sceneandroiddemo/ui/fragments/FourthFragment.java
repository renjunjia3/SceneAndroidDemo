package com.scene.sceneandroiddemo.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scene.sceneandroiddemo.BaseFragment;
import com.scene.sceneandroiddemo.R;
import com.scene.sceneandroiddemo.adapter.TimeLineAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class FourthFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    public static FourthFragment instance() {
        FourthFragment view = new FourthFragment();
        return view;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment4;
    }

    @Override
    public void init() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("这是第" + (i + 1) + "条");
        }
        TimeLineAdapter adapter=new TimeLineAdapter(getActivity(),mList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}