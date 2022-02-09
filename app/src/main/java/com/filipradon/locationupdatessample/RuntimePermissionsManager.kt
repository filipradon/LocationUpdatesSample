package com.filipradon.locationupdatessample

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RuntimePermissionsManager(private val activity: Activity) {

    fun arePermissionsGranted(permissions: Array<String>): Boolean {
        return permissions.all { permission -> ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED }
    }

    fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    companion object {

        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        val ACCESS_BACKGROUND_LOCATION
            get() = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            } else {
                throw IllegalAccessException("This permission does not exist for this ${android.os.Build.VERSION.SDK_INT} Android version")
            }

        const val REQUEST_CODE_ACCESS_FINE_LOCATION = 2501
        const val REQUEST_CODE_ACCESS_FINE_AND_BACKGROUND_LOCATION = 2502
    }
}