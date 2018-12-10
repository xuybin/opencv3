#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_xuybin_opencv3_HelloNDKforJava_sayHelloJava(
        JNIEnv *env,
        jclass  ) {
    std::string hello = "Hello Java from C++";
    return env->NewStringUTF(hello.c_str());
}
