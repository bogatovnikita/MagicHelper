package com.hedgehog.ar154.utils;

import android.content.Context;

import androidx.annotation.Keep;

import com.hedgehog.ar154.AppClass;

@Keep
public class NativeProvider {

    static{
        System.loadLibrary("clean-lib");
    }

    public static native int getOverheatedApps(Context context);

    public static native int[] getGarbageFilesCount(Context context);

    public static native int[] getGarbageSizeArray(Context context);

    public static native String[] getFolders();

    public static native int calculateWorkingMinutes(Context context, int percent);

    public static void saveToPreferences(String fieldName, long time){
        PreferencesProvider.INSTANCE.saveToPreferences(fieldName, time);
    }

    public static native long getRamUsage(Context context, long ramTotal, long ramPart);

    public static native void boost(Context context);
    public static native void powerLow(Context context);
    public static native void powerMedium(Context context);
    public static native void powerHigh(Context context);
    public static native void junk(Context context);
    public static native void cpu(Context context);

    public static native boolean checkRamOverload(AppClass instance);

    public static native int calculateTemperature(Context arg0, int temp);

    public static native boolean checkBatteryDecrease(Context context);

    public static native int getOverloadedPercents(Context context);
}

