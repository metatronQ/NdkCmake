package com.example.ndk_source;

public class NativeProvider {
    static {
        System.loadLibrary("jni_lib");
    }

    public native static String printfC();

    public native static int init(String pcmPath, int audioChannels, int bitRate, int sampleRate, String mp3Path);

    public native static void encode();

    public native static void destroy();
}
