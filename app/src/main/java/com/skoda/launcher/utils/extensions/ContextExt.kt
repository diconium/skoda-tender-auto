package com.skoda.launcher.utils.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


fun Context.getColorCompat(@ColorRes resourceId: Int) = ContextCompat.getColor(this, resourceId)

/** Determines if the context calling has the required permission
 * @param context - the IPC context
 * @param permissions - The permissions to check
 * @return true if the IPC has the granted permission
 */
fun Context.hasPermission(permission: String): Boolean {
    val res = this.checkCallingOrSelfPermission(permission)
    return res == PackageManager.PERMISSION_GRANTED
}