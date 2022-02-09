package com.example.ndk_source.util

import android.util.Log
import android.content.Context

/**
 * @desc: 通用的日志类，包装了Tag 格式：[类名].[方法名]:[代码行]
 * 参考链接 @html: https://blog.csdn.net/qq_39620460/article/details/109740153
 */
object LogUtil {

    private var BASE_CLASS_PATH = "com.example."

    /**
     * 需要先调用指定包名
     */
    fun packageName(context: Context): LogUtil {
        BASE_CLASS_PATH = context.packageName
        return this
    }

    /**
     * 手动指定包名，一般不调用，除非context难以获取
     */
    fun manualPkgName(name: String) {
        BASE_CLASS_PATH = name
    }

    fun v(msg: String){
        Log.v(getCallerInfo(),msg)
    }

    fun d(msg: String){
        Log.d(getCallerInfo(),msg)
    }

    fun i(msg: String){
        Log.i(getCallerInfo(),msg)
    }

    fun w(msg: String){
        Log.w(getCallerInfo(),msg)
    }

    fun e(msg: String) {
        Log.e(getCallerInfo(), msg)
    }

    private fun getCallerInfo(): String {
        val stackTrace = Thread.currentThread().stackTrace
        //0 VMStack.getThreadStackTrace
        //1 Thread.getStackTrace
        //2 LogUtil.getCallerInfo
        //3 LogUtil.e
        //4 Caller
        val caller = stackTrace[4]
        return "${simplifyClassName(caller.className)}.${caller.methodName}:${caller.lineNumber}"
    }

    private fun simplifyClassName(className: String): String{
        return className.substringAfter(BASE_CLASS_PATH)
    }
}