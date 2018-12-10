package com.github.xuybin.opencv3

class HelloNDKforKotlin {

    // 成员方法 jni 使用 jobject
    external fun sayHelloKotlin(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("hello")
        }
    }
}