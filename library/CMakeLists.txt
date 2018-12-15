# 设置构建本地库所需的最小cmake版本
cmake_minimum_required(VERSION 3.4.1)

# 构建时显示日志信息
set(CMAKE_VERBOSE_MAKEFILE ON)

# 设置变量libs
set(libs "${CMAKE_SOURCE_DIR}/src/main/jniLibs")

include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)

##命名一个导入的静态库(因为依赖而需导入的第三方库类库)
#add_library(opencv_java3 SHARED IMPORTED)
#
##  指定导入的静态库的源代码（.c/.cpp）、库文件(.a或.so)
#set_target_properties(opencv_java3
#        PROPERTIES IMPORTED_LOCATION
#        ${libs}/${ANDROID_ABI}/libopencv_java3.so
#        )


# 搜索指定的预构建库,并将该路径存储为一个变量
# 因为cmake默认包含了搜索路径中的系统库,所以您只需要指定您想要添加的公共NDK库的名称,cmake在完成构建之前验证这个库是否存在
find_library(
        log-lib  # 设置path变量的名称。
        log #  指定NDK库的名称
        )


# 创建并命名一个共享库,并提供其源代码的相对路径
add_library(
        hello # 设置库的名称。即so文件的名称,产生的so文件为“lib库的名称.so”,在加载的时候“System.loadLibrary("库的名称");”
        SHARED # 将库设置为共享库
        src/main/cpp/example/HelloNDKforJava.cpp # 提供一个或多个（另起一行）源文件的相对路径
        src/main/cpp/example/HelloNDKforKotlin.cpp
        )

# 指定该共享库需要链接的库,静态库会合并到目标库的so文件内
target_link_libraries(
        hello #指定目标库中,与 add_library的库名称一定要相同
        ${log-lib} # 将目标库链接到日志库包含在NDK
        )


add_library(libopencv_java3 SHARED IMPORTED )
set_target_properties(libopencv_java3 PROPERTIES
        IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libopencv_java3.so")

#add_library(libopencv_core STATIC IMPORTED )
#set_target_properties(libopencv_core PROPERTIES
#        IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libopencv_core.a")
#
#add_library(libopencv_objdetect STATIC IMPORTED )
#set_target_properties(libopencv_objdetect PROPERTIES
#        IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libopencv_objdetect.a")
#
#add_library(libopencv_features2d SHARED IMPORTED )
#set_target_properties(libopencv_features2d PROPERTIES
#        IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libopencv_features2d.a")
#
#add_library(libopencv_imgproc SHARED IMPORTED )
#set_target_properties(libopencv_imgproc PROPERTIES
#        IMPORTED_LOCATION "${libs}/${ANDROID_ABI}/libopencv_imgproc.a")



add_library(
        detection_based_tracker
        SHARED
        src/main/cpp/example/DetectionBasedTracker_jni.cpp
)


target_link_libraries(
        detection_based_tracker
        libopencv_java3
        ${log-lib}
        )

# 创建并命名一个共享库,并提供其源代码的相对路径
add_library(
        mixed_sample # 设置库的名称。即so文件的名称,产生的so文件为“lib库的名称.so”,在加载的时候“System.loadLibrary("库的名称");”
        SHARED # 将库设置为共享库
        src/main/cpp/example/jni_part.cpp # 提供一个或多个（另起一行）源文件的相对路径
)

# 指定该共享库需要链接的库,静态库会合并到目标库的so文件内
target_link_libraries(
        mixed_sample #指定目标库中,与 add_library的库名称一定要相同
        libopencv_java3
        ${log-lib} # 将目标库链接到日志库包含在NDK
)