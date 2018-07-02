package com.example.zj.common_android.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zj.common_android.R;


/**
 * Created by zj on 2018/4/1.
 */

public class GlideImageUtil {

    /*public static void loadWebImageNoCache(Context context, String url, ImageView iv) {
        with(context).load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE).config(Bitmap.Config.RGB_565)
                .error(R.mipmap.ic_error).placeholder(R.mipmap.load_imging).into(iv);
    }*/

    public static void loadWebImage(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)//加载图根据具体项目切图设定
                .error(R.mipmap.ic_launcher)//错误图
                .thumbnail(0.1f)
                .dontAnimate()
                .into(iv);
    }

    public static void loadResourceImage(Context context, int resourceId, ImageView iv) {
        Glide.with(context)
                .load(resourceId)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .thumbnail(0.1f)
                .dontAnimate()
                .into(iv);
    }

    /**
     * 米有缩略图 米有过度图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadWebImageSimple(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(iv);
    }

    public static void loadWebImage(Context context, String url, ImageView iv, int mipmap) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(mipmap)
                .dontAnimate()
                .into(iv);
    }


    /*public static void loadWebImageWithCookie(Context context, String url, ImageView iv) {

        Picasso picasso = new Picasso.Builder(context).downloader(new UrlConnectionDownloader(context) {

            @Override
            protected HttpURLConnection openConnection(Uri path) throws IOException {
                HttpURLConnection conn = super.openConnection(path);

                conn.setRequestProperty("Cookie", HttpConn.getHttpConn().getHttpConfig().getCookies());

                return conn;
            }
        }).build();
        picasso.load(url).config(Bitmap.Config.RGB_565).error(R.mipmap.ic_error).placeholder(R.mipmap.load_imging).into(iv);

    }*/

    /*public static void loadLocalImgNoCache(Context context, String url, ImageView iv) {
        with(context).load(url).resize(dp2px(context, 250), dp2px(context, 250))
                .memoryPolicy(NO_CACHE, NO_STORE)
                .centerCrop().config(Bitmap.Config.RGB_565).error(R.mipmap.ic_error).placeholder(R.mipmap.load_imging).into(iv);
    }

    public static void loadRecImage(Context context, int id, ImageView iv) {
        with(context).load(id).config(Bitmap.Config.ARGB_8888).error(R.mipmap.ic_error).placeholder(R.mipmap.load_imging).into(iv);
    }*/

    public static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /*// 获取Glide磁盘缓存大小
    public String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(new File(Application.getInstance().getCacheDir() + "/" + GlideCatchConfig.GLIDE_CARCH_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    // 清除Glide磁盘缓存，自己获取缓存文件夹并删除方法
    public boolean cleanCatchDisk() {
        return deleteFolderFile(Application.getInstance().getCacheDir() + "/" + GlideCatchConfig.GLIDE_CARCH_DIR, true);
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(Application.getInstance()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(Application.getInstance()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(Application.getInstance()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // 获取指定文件夹内所有文件大小的和
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 按目录删除文件夹文件方法
    private boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
*/

}
