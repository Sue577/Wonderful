package com.zucc.wsq.a31501284.wonderfulwsq;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveSD {
    /**
     * 保存图片到指定文件夹,将图片保存到本地时进行压缩, 即将图片从Bitmap形式变为File形式时进行压缩,
     * 
     * @param imgName 图片名称
     * @param bitmap 处理图片对象
     * 
     * */
    public static void saveBitmap(String imgName, Bitmap bitmap) {
        String sdpath = "/sdcard/";
        
        File file = new File(sdpath, imgName);
        if (file.exists()) {
            file.delete();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 > 500) {
            // 循环判断如果压缩后图片是否大于500kb继续压缩
            baos.reset();
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
