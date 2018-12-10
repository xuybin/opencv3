#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_xuybin_opencv3_HelloNDKforKotlin_sayHelloKotlin(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello Kotlin from C++";
    return env->NewStringUTF(hello.c_str());
}
