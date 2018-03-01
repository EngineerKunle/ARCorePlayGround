package teamkunle.co.uk.arcoreplayground.app.ui.arcore

import android.os.Bundle
import android.util.Log
import teamkunle.co.uk.arcoreplayground.R
import teamkunle.co.uk.arcoreplayground.base.BaseActivity

class ARCoreActivity : BaseActivity(), ARCoreView {

    private val tag = ARCoreActivity::class.simpleName
    private val presenter = ARCorePresenterImpl<ARCoreView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "on create called")
    }

    override fun onResume() {
        super.onResume()
//        custom_surface_view.onResume()
    }

    override fun onPause() {
        super.onPause()
//        custom_surface_view.onPause()
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
