package teamkunle.co.uk.arcoreplayground.app.ui.cameradraw

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import kotlinx.android.synthetic.main.activity_arcore.*
import teamkunle.co.uk.arcoreplayground.R
import teamkunle.co.uk.arcoreplayground.base.BaseActivity
import teamkunle.co.uk.arcoreplayground.utils.CameraUtils

class CameraDrawActivity : BaseActivity(), CameraDrawView {

    private val tag = CameraDrawActivity::class.simpleName
    private val presenter = CameraDrawPresenterImpl<CameraDrawView>()

    private var userRequestInstall : Boolean = false
    private  var session : Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        Log.d(tag, "on create called")

        userRequestInstall = false
    }

    override fun onResume() {
        super.onResume()

        if (!CameraUtils.hasCameraPermission(this)) {
            CameraUtils.requestCameraPermission(this)
            return
        }

        if(session == null) {

            var exception : Exception? = null
            var message : String? = null

            try {
                session = Session(this)

            } catch (e : UnavailableArcoreNotInstalledException) {
                message = "Please Install ARCore"
                exception = e

            } catch (e : UnavailableUserDeclinedInstallationException) {
                message = "Please Install ARCore"
                exception = e

            } catch (e : UnavailableApkTooOldException) {
                message = "Please Update ARCore"
                exception = e

            } catch (e : UnavailableSdkTooOldException) {
                message = "Please Update app"
                exception = e

            } catch (e : Exception) {
                message = "This device does not support AR"
                exception = e
            }

            message?.let {
                Log.e(tag, "Exception creating session ", exception)
                return
            }
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if(hasFocus) {
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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
