package com.example.contactrandom.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import javax.inject.Inject

/** Created by marlon on 28/5/23. **/
class PermissionManager @Inject constructor(var context: Context) {

    /*private fun verifyPermission(permission: ArrayList<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permission.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        val permissionArray = permission.toTypedArray()
        if (havePermission(permissionArray)) {
           // haConcedidoPermisos = true
        } else {
            //requestPermission(permissionArray)
        }
    }*/ // TEST TO DO

    fun havePermission(permisos: Array<String>): Boolean {
        return permisos.all {
            return ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermission(activity: Activity, permissionName: String, codePermission: Int) {
        requestPermissions(
            activity,
            arrayOf(permissionName),
            codePermission)
    }


}