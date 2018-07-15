package com.zucc.wsq.a31501284.wonderfulwsq;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class BitmapToByte {
    /**
     * 保存图片到指定文件夹,将图片保存到本地时进行压缩, 即将图片从Bitmap形式变为File形式时进行压缩,
     *  */
    public static byte[] saveBitmap(Bitmap bitmap) {
    
        int size=bitmap.getWidth()*bitmap.getHeight()*4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
        int options = 100;
        /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 > 500) {
            // 循环判断如果压缩后图片是否大于500kb继续压缩
            baos.reset();
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        //将字节数组输出流转化为字节数组byte[]
        
        byte[] imagedata=baos.toByteArray();
        return imagedata;
   
    }
}
