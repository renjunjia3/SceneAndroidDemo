package com.scene.sceneandroiddemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by scene on 16/01/27.
 */
public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private ProgressDialog progressDialog;
    public Context mContext;
    private Toast mToast;
    //权限的类型用于同一页面多个操作需要同一权限的问题
    private int permissionType = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        ButterKnife.bind(this);
        mContext = this;
    }

    protected abstract int setLayoutResId();

    /**
     * 显示dialog
     *
     * @param message
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.setMessage("加载中...");
        dismissProgressDialog();
        progressDialog.show();
    }

    /**
     * 隐藏
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示Toast
     *
     * @param message
     */
    public void showToast(String message) {
        if (message == null || TextUtils.isEmpty(message)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 通过注解验证是否有相机权限
     * 没有则需要申请权限
     * ps:该方法不能有参数
     */
    @AfterPermissionGranted(PermissionConfig.RC_CAMERA_PERM)
    public void applyCameraPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onCameraPermissionSuccess(getPermissionType());
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.camera_permission),
                    PermissionConfig.RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    /**
     * 通过注解验证是否有存储空间权限
     * 没有则需要申请权限
     * ps:该方法不能有参数
     */
    @AfterPermissionGranted(PermissionConfig.RC_Storage_PERM)
    public void applyStoragePermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            onStoragePermissionSuccess(getPermissionType());
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.storage_permission),
                    PermissionConfig.RC_Storage_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 允许授权
     * 这个方法可以不用管
     *
     * @param perms
     */
    @Override
    public void onPermissionsGranted(List<String> perms) {
    }

    /**
     * 拒绝授权
     * 交互友好为了在用户拒绝授权时响应用户按下效果
     *
     * @param perms
     */
    @Override
    public void onPermissionsDenied(List<String> perms) {
        for (String perm : perms) {
            if (perm.equals(Manifest.permission.CAMERA)) {
                showToast("相机权限被拒绝");
            }
            if (perm.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showToast("存储空间权限被拒绝");
            }
        }
    }

    /**
     * 成功获取相机权限后的操作
     * 在这儿不做处理需要的时候再处理
     *
     * @return type 添加一个类型处理同一页面多个地方需要相同权限
     */
    public void onCameraPermissionSuccess(int type) {
    }

    /**
     * 成功获取存储空间权限后的操作
     * 在这儿不做处理需要的时候再处理
     *
     * @return type 添加一个类型处理同一页面多个地方需要相同权限
     */
    public void onStoragePermissionSuccess(int type) {
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    public int getPermissionType() {
        return permissionType;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
