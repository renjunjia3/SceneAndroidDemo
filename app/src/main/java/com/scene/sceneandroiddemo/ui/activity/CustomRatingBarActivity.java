package com.scene.sceneandroiddemo.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.scene.mylib.view.ratingbar.ProperRatingBar;
import com.scene.mylib.view.ratingbar.RatingListener;
import com.scene.sceneandroiddemo.BaseActivity;
import com.scene.sceneandroiddemo.R;

import butterknife.Bind;

/**
 * 自定义评星条
 * 解决不同分辨率适配问题
 */
public class CustomRatingBarActivity extends BaseActivity {
    @Bind(R.id.ratingBar)
    ProperRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_custom_rating_bar;
    }

    private void init() {
        ratingBar.setListener(new RatingListener() {
            @Override
            public void onRatePicked(ProperRatingBar ratingBar) {
                showToast("当前选择的是：" + ratingBar.getRating());
            }
        });
    }
}
