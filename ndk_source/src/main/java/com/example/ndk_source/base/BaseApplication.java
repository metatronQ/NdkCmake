package com.example.ndk_source.base;

import android.app.ActivityManager;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import java.util.List;

public abstract class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            onCreateWithMainProcess();
        } else {
            onCreateWithOtherProcess();
        }
    }

    protected abstract void onCreateWithMainProcess();

    protected abstract void onCreateWithOtherProcess();

    private boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
