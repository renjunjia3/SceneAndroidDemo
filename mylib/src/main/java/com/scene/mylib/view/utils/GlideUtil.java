package com.scene.mylib.view.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 封装的Glide图片处理库
 * Created by scene on 16/03/11.
 */
public class GlideUtil {

    public static void loadImage(String url, ImageView imageView, Context context) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, Fragment fragment) {
        Glide.with(fragment).load(url).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, android.app.Fragment fragment) {
        Glide.with(fragment).load(url).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, Activity activity) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, FragmentActivity activity) {
        Glide.with(activity).load(url).into(imageView);
    }
}
