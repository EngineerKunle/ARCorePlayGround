package teamkunle.co.uk.arcoreplayground.surfaceview

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Log
import android.view.SurfaceView


class CameraPreview(context: Context?) : SurfaceView(context) {

    private lateinit var camera: CameraDevice
    private lateinit var cameraManager: CameraManager

    private lateinit var cameraId : String

    private val tag : String? = CameraPreview::javaClass.name

    companion object {
        private val tag : String? = CameraPreview::class.simpleName
    }


    //TODO : finish here create anonymous class for open camera param
    /*
    * https://www.programcreek.com/java-api-examples/index.php?api=android.hardware.camera2.CameraManager
    *
    * https://medium.com/@gauravpandey_34933/how-to-camera2-api-in-android-576fd23650ea
    *
    *
    * */

    fun initCamera() {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraManager.cameraIdList.forEach {
            Log.d(tag, "list of camera device $it")
        }
        Log.d(tag, "size of camera device list " + cameraManager.cameraIdList.size)

        cameraId = cameraManager.cameraIdList[0]

        var cameraCharacteristics : CameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

        var map : StreamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

    }


}