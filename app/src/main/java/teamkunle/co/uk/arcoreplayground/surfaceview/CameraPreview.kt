package teamkunle.co.uk.arcoreplayground.surfaceview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.util.Log
import android.view.SurfaceView
import teamkunle.co.uk.arcoreplayground.utils.CameraUtils


class CameraPreview(context: Context?) : SurfaceView(context) {

    private var camera : CameraDevice? = null
    private lateinit var cameraManager: CameraManager
    private lateinit var customSurfaceView: CustomSurfaceView

    private lateinit var cameraId : String
    private val tag : String? = CameraPreview::javaClass.name

    companion object {
        private val tag : String? = CameraPreview::class.simpleName
    }

    @SuppressLint("MissingPermission")
    fun initCamera(activity: Activity) {
        this.customSurfaceView = customSurfaceView

        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraManager.cameraIdList.forEach {
            Log.d(tag, "list of camera device $it")
        }
        Log.d(tag, "size of camera device list " + cameraManager.cameraIdList.size)

        cameraId = cameraManager.cameraIdList[0]

//        var cameraCharacteristics : CameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
//
//        var map : StreamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        try {
            if (CameraUtils.hasCameraPermission(activity)) {
                cameraManager.openCamera(cameraId, cameraStateCallback, null)
            } else {
                CameraUtils.requestCameraPermission(activity)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    private val cameraStateCallback = object : CameraDevice.StateCallback(){
        override fun onOpened(cameraDevice: CameraDevice?) {
            Log.d(tag, "Camera is opened")
            camera = cameraDevice
        }

        override fun onDisconnected(cameraDevice: CameraDevice?) {

        }

        override fun onError(cameraDevice: CameraDevice?, error: Int) {

        }
    }
}