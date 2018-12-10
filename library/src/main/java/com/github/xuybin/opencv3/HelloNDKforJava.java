package com.github.xuybin.opencv3;


public class HelloNDKforJava {
    static {
        System.loadLibrary("hello");
    }
    // 静态方法 jni 使用 jclass
    public static native String sayHelloJava();
}
