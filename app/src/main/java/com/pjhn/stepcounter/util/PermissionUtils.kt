package com.pjhn.stepcounter.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

object PermissionUtils {

    private val permissionsToCheck = mutableListOf(
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.POST_NOTIFICATIONS
    )

    val permissionsToRequest = mutableListOf<String>()

    fun hasPermissions(context: Context): Boolean {
        permissionsToRequest.clear()

        for (permission in permissionsToCheck) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
            }
        }

        return permissionsToRequest.isEmpty()
    }

    fun hasDeniedPermission(context: Context): Boolean {
        if (hasPermissions(context)) return false
        return permissionsToRequest.any { permission ->
            !shouldShowRequestPermissionRationale(context as Activity, permission)
        }
    }

    fun requestPermissions(
        permissionLauncher: ActivityResultLauncher<Array<String>?>
    ) {
        permissionLauncher.launch(permissionsToRequest.toTypedArray())
    }

    fun openSettings(context: Context) {
        val intent = Intent(
            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            android.net.Uri.fromParts("package", context.packageName, null)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}