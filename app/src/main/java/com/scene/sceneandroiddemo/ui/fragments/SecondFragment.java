package com.scene.sceneandroiddemo.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scene.sceneandroiddemo.BaseFragment;
import com.scene.sceneandroiddemo.MainTabActivity;
import com.scene.sceneandroiddemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SecondFragment extends BaseFragment {

    @Bind(R.id.next)
    Button next;

    public static SecondFragment instance() {
        SecondFragment view = new SecondFragment();
        return view;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment2;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.next)
    public void onClickNext() {

    }
}