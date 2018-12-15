package com.github.xuybin.opencv3.example;

public class MixedProcessing {
    static {
        System.loadLibrary("mixed_sample");
    }
     // 用于 tutorial-2-mixedprocessing的JNI封装
    // Java_com_github_xuybin_opencv3_MixedProcessing_FindFeatures
    public  static native void FindFeatures(long matAddrGr, long matAddrRgba);
}
