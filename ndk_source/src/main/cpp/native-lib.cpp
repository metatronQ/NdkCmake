#include <jni.h>
#include "lame/function_realization/Mp3Encoder.h"

//
// Created by bjy on 2022/1/20.
//


Mp3Encoder *encoder;
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ndk_1source_NativeProvider_printfC(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("write from c++");
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_ndk_1source_NativeProvider_init(JNIEnv *env, jclass thiz, jstring pcm_path,
                                                 jint audio_channels, jint bit_rate,
                                                 jint sample_rate, jstring mp3_path) {
    const char *pcmPath = env->GetStringUTFChars(pcm_path, NULL);
    const char *mp3Path = env->GetStringUTFChars(mp3_path, NULL);
    encoder = new Mp3Encoder();
    int ret = encoder->Init(pcmPath, mp3Path, sample_rate, audio_channels, bit_rate);
    env->ReleaseStringUTFChars(mp3_path, mp3Path);
    env->ReleaseStringUTFChars(pcm_path, pcmPath);
    return ret;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_ndk_1source_NativeProvider_encode(JNIEnv *env, jclass thiz) {
    if (encoder) encoder->Encode();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_ndk_1source_NativeProvider_destroy(JNIEnv *env, jclass thiz) {
    if (encoder) encoder->Destory();
}