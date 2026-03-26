package com.example.pp3

import android.app.ActivityManager
import android.content.Context
import android.os.Build

object DeviceUtils {
    // Check if the device has enough RAM (e.g., > 4GB) to run a small model
    fun isCapableDevice(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        // Total RAM in bytes. 4GB = 4 * 1024 * 1024 * 1024
        val minRamBytes = 3.5 * 1024 * 1024 * 1024
        return memInfo.totalMem > minRamBytes
    }

    // Get the chipset name (useful for debugging performance)
    fun getChipset(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Build.SOC_MODEL
        } else {
            Build.HARDWARE
        }
    }
}