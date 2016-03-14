package com.scene.sceneandroiddemo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.scene.chooseimagelib.ImageChooseUtil;
import com.scene.chooseimagelib.simplecropimage.CropImage;
import com.scene.sceneandroiddemo.BaseActivity;
import com.scene.sceneandroiddemo.FileUtil;
import com.scene.sceneandroiddemo.PermissionConfig;
import com.scene.sceneandroiddemo.R;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 图片选择与裁剪
 * 目前通过相册选取照片进行裁剪有问题
 * 以后在慢慢优化
 * Created by scene on 16/01/27.
 */
public class SelectAndCropImageActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.img)
    ImageView img;

    //图片缓存uri
    private Uri mTempUri, mTempCropUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_select_and_crop_image;
    }

    @OnClick(R.id.galley)
    public void onClickGalley() {
        //检查权限
        setPermissionType(PermissionConfig.TYPE_PERMISSION_1);
        applyStoragePermission();
    }

    @OnClick(R.id.camera)
    public void onClickCamera() {
        setPermissionType(PermissionConfig.TYPE_PERMISSION_1);
        applyCameraPermission();
    }

    @OnClick(R.id.galleyCrop)
    public void onClickGalleyCrop() {
        setPermissionType(PermissionConfig.TYPE_PERMISSION_2);
        applyStoragePermission();
    }

    @OnClick(R.id.cameraCrop)
    public void onClickCameraCrop() {
        setPermissionType(PermissionConfig.TYPE_PERMISSION_2);
        applyCameraPermission();
    }

    private void initToolBar() {
        toolbar.setTitle("图片选择与裁剪");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 相机权限成功
     */
    @Override
    public void onCameraPermissionSuccess(int type) {
        if (type == PermissionConfig.TYPE_PERMISSION_1) {
            initTempUri();
            ImageChooseUtil.getInstance().openCamera(mContext, mTempUri, false);
        } else if (type == PermissionConfig.TYPE_PERMISSION_2) {
            initTempUri();
            ImageChooseUtil.getInstance().openCamera(mContext, mTempUri, true);
        }
    }

    /**
     * 存储空间权限成功
     *
     * @param type
     */
    @Override
    public void onStoragePermissionSuccess(int type) {
        if (type == PermissionConfig.TYPE_PERMISSION_1) {
            ImageChooseUtil.getInstance().openGalley(mContext, false);
        } else if (type == PermissionConfig.TYPE_PERMISSION_2) {
            ImageChooseUtil.getInstance().openGalley(mContext, true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是否是4.4以上版本
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (resultCode != Activity.RESULT_OK) {
            FileUtil.delTempFile(mContext);
            return;
        }
        switch (requestCode) {
            case ImageChooseUtil.GALLEY:
                if (null != data) {
                    Uri uri = data.getData();
                    img.setImageURI(uri);
                }
                break;
            case ImageChooseUtil.GALLEY_KITKAT:
                if (null != data) {
                    Uri uri = data.getData();
                    // 先将这个uri转换为path，然后再转换为uri
                    String thePath = FileUtil.getPath(mContext, uri);
                    Uri uri2 = Uri.fromFile(new File(thePath));
                    img.setImageURI(uri2);
                }
                break;
            case ImageChooseUtil.CAMERA:
                if (mTempUri != null) {
                    img.setImageURI(mTempUri);
                }
                break;
            case ImageChooseUtil.GALLEY_CROP:
                if (null != data) {
                    initCropUri();
                    Uri uri = data.getData();
                    String thePath = FileUtil.getPath(mContext, uri);
                    ImageChooseUtil.getInstance().cropImageUri(mContext, thePath, mTempUri, 300);
                }
                break;
            case ImageChooseUtil.GALLEY_KITKAT_CROP:
                if (null != data) {
                    Uri uri = data.getData();
                    // 先将这个uri转换为path，然后再转换为uri
                    String thePath = FileUtil.getPath(mContext, uri);
                    Uri uri2 = Uri.fromFile(new File(thePath));
                    ImageChooseUtil.getInstance().cropImageUri(mContext, uri2.getPath(), mTempCropUri, 300);
                }
                break;
            case ImageChooseUtil.CAMERA_CROP:
                if (mTempUri != null) {
                    ImageChooseUtil.getInstance().cropImageUri(mContext, mTempUri.getPath(), mTempUri, 300);
                }
            case ImageChooseUtil.CROP:
                String path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path != null) {
                    img.setImageBitmap(BitmapFactory.decodeFile(path));
                }
                break;
            default:
                break;
        }

    }

    /**
     * 获取缓存uri
     */
    private void initTempUri() {
        FileUtil.delTempFile(mContext);
        mTempUri = FileUtil.getTempUri(mContext);
    }

    /**
     * 获取裁剪缓存uri
     */
    private void initCropUri() {
        mTempCropUri = FileUtil.getTempUri(mContext);
    }
}
