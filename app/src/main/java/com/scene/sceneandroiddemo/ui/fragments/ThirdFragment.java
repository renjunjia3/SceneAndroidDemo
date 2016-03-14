package com.scene.sceneandroiddemo.ui.fragments;

import android.graphics.Color;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.scene.customfresco.FrescoImageView;
import com.scene.customfresco.FrescoZoomImageView;
import com.scene.customfresco.custom.CustomProgressBar;
import com.scene.sceneandroiddemo.BaseFragment;
import com.scene.sceneandroiddemo.R;

import butterknife.Bind;


public class ThirdFragment extends BaseFragment {
    @Bind(R.id.img)
    FrescoImageView img;

    @Bind(R.id.img2)
    FrescoZoomImageView img2;

    public static ThirdFragment instance() {
        ThirdFragment view = new ThirdFragment();
        return view;
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment3;
    }

    @Override
    public void init() {
        //必须在loadview之前调用才会生效
        img.setShowProgress(true);
        img.loadView("https://raw.githubusercontent.com/renjunjia3/MyTest/master/app/src/main/res/drawable/png6.jpg", R.mipmap.ic_launcher);
        //设置圆形和边框 无参数时就是原型
        img.asCircle(10, Color.RED);
        //关闭gif
        img.setAnim(false);

    }

}