package teamkunle.co.uk.arcoreplayground.surfaceview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.hardware.camera2.params.StreamConfigurationMap
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.SurfaceView
import android.view.TextureView
import teamkunle.co.uk.arcoreplayground.utils.CameraUtils
import java.util.*


class CameraPreview(context: Context?) : SurfaceView(context) {

    private var camera : CameraDevice? = null
    private var cameraCaptureSessions: CameraCaptureSession? = null
    private var origianalTextureView : TextureView? = null

    private lateinit var captureRequestBuilder : CaptureRequest.Builder
    private lateinit var cameraManager: CameraManager
    private lateinit var previewSize : Size
    private lateinit var backgroundThread : HandlerThread
    private lateinit var backgroundHandler : Handler

    private lateinit var cameraId : String
    private val tag : String? = CameraPreview::javaClass.name

    companion object {
        private val tag : String? = CameraPreview::class.simpleName
    }


    fun initCamera(activity: Activity, textureView: TextureView?) {

        backgroundThread = HandlerThread("camera background")
        backgroundThread.start()
        backgroundHandler = Handler(backgroundThread.looper)

        origianalTextureView = textureView
        origianalTextureView?.surfaceTextureListener = surfaceTextureImpl


        if (CameraUtils.hasCameraPermission(activity)) {
            openCamera()
        } else {
            CameraUtils.requestCameraPermission(activity)
        }

    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraManager.cameraIdList.forEach {
            Log.d(tag, "list of camera device $it")
        }
        Log.d(tag, "size of camera device list " + cameraManager.cameraIdList.size)

        cameraId = cameraManager.cameraIdList[0]

        var cameraCharacteristics : CameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

        var map : StreamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        previewSize = map.getOutputSizes(SurfaceTexture::class.java)[0]

        try {
            cameraManager.openCamera(cameraId, cameraStateCallback, null)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    private val cameraStateCallback = object : CameraDevice.StateCallback(){
        override fun onOpened(cameraDevice: CameraDevice?) {
            Log.d(tag, "Camera is opened")
            camera = cameraDevice
            createCameraPreview()
        }

        override fun onDisconnected(cameraDevice: CameraDevice?) {

        }

        override fun onError(cameraDevice: CameraDevice?, error: Int) {

        }
    }

    private val cameraStateCaptionCallBack = object : CameraCaptureSession.StateCallback() {

        override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession?) {
            if (camera == null) {
                return
            }

            cameraCaptureSessions = cameraCaptureSession
            updatePreview()
        }

        override fun onConfigured(cameraCaptureSession: CameraCaptureSession?) {

        }
    }

    private val surfaceTextureImpl = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {

        }

        override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {

        }

        override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
            return false
        }

        override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
            openCamera()
        }
    }

    private fun createCameraPreview() {
        try {

            if (origianalTextureView?.surfaceTexture == null) {
                Log.d(tag, "it is null")
            } else {
                Log.d(tag, "it is not null")
            }

            val surfaceTexture : SurfaceTexture? = origianalTextureView?.surfaceTexture
            surfaceTexture?.setDefaultBufferSize(previewSize.width, previewSize.height)

            val surface = Surface(surfaceTexture)

            captureRequestBuilder = camera!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)
            camera?.createCaptureSession(Arrays.asList(surface), cameraStateCaptionCallBack, null)
        } catch (e : CameraAccessException) {
            e.printStackTrace()
        }
    }

    //TODO : here is null
    private fun updatePreview() {
        if (camera == null) {
            Log.e(tag, "camera is closed or null")
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions?.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler)

        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

}