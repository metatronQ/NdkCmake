# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("ndk_source")

# jni c层方法库
add_library( # Sets the name of the library.
        jni_lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp)

include_directories(include libmp3lame)

# 定义lame相关文件的全局变量
file(
        GLOB
        lame-path
#        lame.h移至libmp3lame
#        ${CMAKE_SOURCE_DIR}/lame/include/*.h
#        ${CMAKE_SOURCE_DIR}/lame/include/*.c
        ${CMAKE_SOURCE_DIR}/lame/function_realization/*.h
        ${CMAKE_SOURCE_DIR}/lame/function_realization/*.cpp
        ${CMAKE_SOURCE_DIR}/lame/libmp3lame/*.h
        ${CMAKE_SOURCE_DIR}/lame/libmp3lame/*.c
        ${CMAKE_SOURCE_DIR}/lame/libmp3lame/vector/*.h
        ${CMAKE_SOURCE_DIR}/lame/libmp3lame/vector/*.c
        ${CMAKE_SOURCE_DIR}/lame/libmp3lame/i386/*.h
        ${CMAKE_SOURCE_DIR}/lame/libmp3lame/i386/*.c
)

add_library(lame_lib SHARED ${lame-path})

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        jni_lib

        lame_lib

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})