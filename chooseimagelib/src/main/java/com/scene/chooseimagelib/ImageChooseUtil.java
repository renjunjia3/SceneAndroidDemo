package com.scene.chooseimagelib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.scene.chooseimagelib.simplecropimage.CropImage;

/**
 * 图片选择工具类
 * Created by scene on 16/01/27.
 */
public class ImageChooseUtil {
    public static final int GALLEY = 10001;
    public static final int GALLEY_CROP = 10002;
    public static final int GALLEY_KITKAT = 10003;
    public static final int GALLEY_KITKAT_CROP = 10004;
    public static final int CAMERA = 10005;
    public static final int CAMERA_CROP = 10006;
    public static final int CROP = 10007;


    private static ImageChooseUtil instance = null;

    public static synchronized ImageChooseUtil getInstance() {
        if (instance == null) {
            instance = new ImageChooseUtil();
        }
        return instance;
    }

    /**
     * 打开相册
     *
     * @param mContext
     */
    public void openGalley(Context mContext, boolean isCrop) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (isCrop) {
                ((Activity) mContext).startActivityForResult(intent, GALLEY_CROP);
            } else {
                ((Activity) mContext).startActivityForResult(intent, GALLEY);
            }
        } else {
            /**
             * 4.4以上版本用这样的方式出来
             */
            Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (isCrop) {
                ((Activity) mContext).startActivityForResult(picture, GALLEY_KITKAT_CROP);
            } else {
                ((Activity) mContext).startActivityForResult(picture, GALLEY_KITKAT);
            }
        }
    }

    /**
     * 打开相机
     */
    public void openCamera(Context mContext, Uri mTempUri, boolean isCrop) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
        // capture
        //限制拍摄角度，解决三星机器问题
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempUri);
        if (isCrop) {
            ((Activity) mContext).startActivityForResult(intent, CAMERA_CROP);
        } else {
            ((Activity) mContext).startActivityForResult(intent, CAMERA);
        }
    }

    /**
     * 调用系统裁剪
     *
     * @param uri
     */
    public void cropImageUri(Context mContext, Uri uri, Uri mTempUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        ((Activity) mContext).startActivityForResult(intent, CROP);
    }

    /**
     * 第三方的裁剪
     *
     * @param mContext
     * @param filepath
     */
    public void cropImageUri(Context mContext, String filepath, Uri mTempUri,int imgSize) {
        // create explicit intent
        Intent intent = new Intent(mContext, CropImage.class);
        // tell CropImage activity to look for image to crop
        intent.putExtra(CropImage.IMAGE_PATH, filepath);
        intent.putExtra(CropImage.IMAGE_SAVE_PATH, mTempUri);
        // allow CropImage activity to rescale image
        intent.putExtra(CropImage.SCALE, true);
        // if the aspect ratio is fixed to ratio 3/2
        intent.putExtra(CropImage.ASPECT_X, 1);
        intent.putExtra(CropImage.ASPECT_Y, 1);
        intent.putExtra(CropImage.OUTPUT_X,imgSize);
        intent.putExtra(CropImage.OUTPUT_Y,imgSize);

        // start activity CropImage with certain request code and listen
        // for result
        ((Activity) mContext).startActivityForResult(intent, CROP);
    }

}
