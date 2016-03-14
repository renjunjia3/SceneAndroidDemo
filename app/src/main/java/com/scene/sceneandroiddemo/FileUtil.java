package com.scene.sceneandroiddemo;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by scene on 16/01/27.
 */
public class FileUtil {
    public static final String IMAGE_TEMP_DIR = "/SCENE/temp/image/";

    /**
     * 获取sdk根目录
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getImageDir(Context context, String uniqueName) {

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            return new File(Environment.getExternalStorageDirectory().getPath()
                    + uniqueName);

        } else {
            return new File(context.getCacheDir().getPath() + File.separator
                    + uniqueName);
        }
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes,磁盘剩余空间
     */
    public static long getUsableSpace(File path) {
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    /**
     * 对空间进行判断，如果不存在进行创建
     *
     * @param context
     */
    public static File makeImageDirs(Context context) {
        File imageDir = getImageDir(context, IMAGE_TEMP_DIR);
        if (imageDir != null) {
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
        }

        return imageDir;

        // getUsableSpace(file) > size
    }

    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 保存图片文件到本地
     *
     * @param imageName
     * @param bitmap
     * @param context
     * @throws Exception
     */
    public static void saveImage(String imageName, Bitmap bitmap,
                                 Context context) throws Exception {

        File file = makeImageDirs(context);
        File imageFile = new File(file.getPath(), imageName);

        FileOutputStream outStream = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.flush();
        outStream.close();

    }

    public static void saveImage(String imageName, Context context)
            throws Exception {
        File file = makeImageDirs(context);

        File temp = getTempFile(context);
        FileInputStream inputStream = new FileInputStream(temp);

        File imageFile = new File(file.getPath(), imageName);
        FileOutputStream outputStream = new FileOutputStream(imageFile);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void rcursionDeleteFile(File file) {
        if (file.isFile()) {
            System.out.print(file);
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                System.out.print(file);
                file.delete();
                return;
            }
            for (File f : childFile) {
                rcursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * @param context
     * @return
     */
    public static Uri getTempUri(Context context) {
        return Uri.fromFile(getTempFile(context));
    }

    //删除tempfile
    public static void delTempFile(Context context) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath() + IMAGE_TEMP_DIR);
        if (file.exists()) {

            rcursionDeleteFile(file);
        }
    }

    public static File getTempFile(Context context) {
        if (hasSd()) {
            makeImageDirs(context);
            String path = Environment.getExternalStorageDirectory()
                    .getPath() + IMAGE_TEMP_DIR + System.currentTimeMillis() + ".png";
            File file = new File(path);

            Log.e("test", "image file path = " + path);
            try {

                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return file;
        }

        return null;
    }

    public static boolean hasSd() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

}
