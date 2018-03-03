package teamkunle.co.uk.arcoreplayground.app.ui.arcore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_arcore.*
import teamkunle.co.uk.arcoreplayground.R
import teamkunle.co.uk.arcoreplayground.base.BaseActivity
import teamkunle.co.uk.arcoreplayground.utils.CameraUtils

class ARCoreActivity : BaseActivity(), ARCoreView {

    private val tag = ARCoreActivity::class.simpleName
    private val presenter = ARCorePresenterImpl<ARCoreView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "on create called")
    }

    override fun onResume() {
        super.onResume()
        if (!CameraUtils.hasCameraPermission(this)) {
            CameraUtils.requestCameraPermission(this)
            return
        }
        custom_surface_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        custom_surface_view.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(!CameraUtils.hasCameraPermission(this)) {
            Toast.makeText(this, "This app needs to use Camera permission ", Toast.LENGTH_LONG).show()

            if (!CameraUtils.shouldRequestCameraPermission(this)) {
                CameraUtils.launchPermissionSettings(this)
            }
            finish()
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun initView() {
        presenter.attachView(this)
    }

    override fun getLayOutResourcedId(): Int {
        return R.layout.activity_arcore
    }
}
