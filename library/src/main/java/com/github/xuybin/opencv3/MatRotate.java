package com.github.xuybin.opencv3;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import org.opencv.core.Mat;

import static org.opencv.core.Core.flip;
import static org.opencv.core.Core.transpose;

public class MatRotate {

    //顺时针90
    public static Mat matRotateClockWise90(Mat src)
    {
        if (src.empty())
        {
            return src;
        }
        // 矩阵转置
        transpose(src, src);
        //0: 沿X轴翻转； >0: 沿Y轴翻转； <0: 沿X轴和Y轴翻转
        flip(src, src, 1);// 翻转模式，flipCode == 0垂直翻转（沿X轴翻转），flipCode>0水平翻转（沿Y轴翻转），flipCode<0水平垂直翻转（先沿X轴翻转，再沿Y轴翻转，等价于旋转180°）
        return src;
    }

    //顺时针180
    public static Mat matRotateClockWise180(Mat src)
    {
        if (src.empty())
        {
            return src;
        }
        //0: 沿X轴翻转； >0: 沿Y轴翻转； <0: 沿X轴和Y轴翻转
        flip(src, src, 0);// 翻转模式，flipCode == 0垂直翻转（沿X轴翻转），flipCode>0水平翻转（沿Y轴翻转），flipCode<0水平垂直翻转（先沿X轴翻转，再沿Y轴翻转，等价于旋转180°）
        flip(src, src, 1);
        return src;
        //transpose(src, src);// 矩阵转置
    }

    //顺时针270
    public static Mat matRotateClockWise270(Mat src)
    {
        if (src.empty())
        {
            return src;
        }
        // 矩阵转置
        //transpose(src, src);
        //0: 沿X轴翻转； >0: 沿Y轴翻转； <0: 沿X轴和Y轴翻转
        transpose(src, src);// 翻转模式，flipCode == 0垂直翻转（沿X轴翻转），flipCode>0水平翻转（沿Y轴翻转），flipCode<0水平垂直翻转（先沿X轴翻转，再沿Y轴翻转，等价于旋转180°）
        flip(src, src, 0);
        return src;
    }

    //逆时针90°
    public static Mat myRotateAntiClockWise90(Mat src)
    {
        if (src.empty()) return src;
        transpose(src, src);
        flip(src, src, 0);
        return src;
    }

    public static Mat mirror(Mat src){
        if (src.empty()) return src;
        flip(src, src, 1);
        return src;
    }

    public static Bitmap setImgSize(Bitmap bm, int newWidth , int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
