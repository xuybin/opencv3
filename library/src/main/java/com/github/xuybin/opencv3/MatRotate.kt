package com.github.xuybin.opencv3

import org.opencv.core.Core.flip
import org.opencv.core.Core.transpose
import org.opencv.core.Mat

//顺时针90
fun matRotateClockWise90(src: Mat): Mat {
    // 矩阵转置
    transpose(src, src)
    //0: 沿X轴翻转； >0: 沿Y轴翻转； <0: 沿X轴和Y轴翻转
    flip(src, src, 1)// 翻转模式，flipCode == 0垂直翻转（沿X轴翻转），flipCode>0水平翻转（沿Y轴翻转），flipCode<0水平垂直翻转（先沿X轴翻转，再沿Y轴翻转，等价于旋转180°）
    return src
}

//顺时针180
fun matRotateClockWise180(src: Mat): Mat {
    //0: 沿X轴翻转； >0: 沿Y轴翻转； <0: 沿X轴和Y轴翻转
    flip(src, src, 0)// 翻转模式，flipCode == 0垂直翻转（沿X轴翻转），flipCode>0水平翻转（沿Y轴翻转），flipCode<0水平垂直翻转（先沿X轴翻转，再沿Y轴翻转，等价于旋转180°）
    flip(src, src, 1)
    return src
    //transpose(src, src);// 矩阵转置
}

//顺时针270
fun matRotateClockWise270(src: Mat): Mat {
    // 矩阵转置
    //transpose(src, src);
    //0: 沿X轴翻转； >0: 沿Y轴翻转； <0: 沿X轴和Y轴翻转
    transpose(src, src)// 翻转模式，flipCode == 0垂直翻转（沿X轴翻转），flipCode>0水平翻转（沿Y轴翻转），flipCode<0水平垂直翻转（先沿X轴翻转，再沿Y轴翻转，等价于旋转180°）
    flip(src, src, 0)
    return src
}

//逆时针90°
fun myRotateAntiClockWise90(src: Mat): Mat {
    transpose(src, src)
    flip(src, src, 0)

    return src
}
