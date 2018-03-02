package teamkunle.co.uk.arcoreplayground.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

const val  cameraPermission = Manifest.permission.CAMERA
const val cameraPermissionCode = 1
class CameraUtils {

    companion object {

        fun hasCameraPermission(activity: Activity) : Boolean {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        }

        fun shouldRequestCameraPermission(activity: Activity) : Boolean {
            return ActivityCompat.shouldShowRequestPermissionRationale(activity, cameraPermission)
        }

        fun requestCameraPermission(activity: Activity) {
            ActivityCompat.requestPermissions(activity, arrayOf(cameraPermission), cameraPermissionCode)
        }

        fun launchPermissionSettings(activity: Activity) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data   = Uri.fromParts("package", activity.packageName, null)
            activity.startActivity(intent)
        }
    }
}