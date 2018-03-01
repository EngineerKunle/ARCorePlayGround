package teamkunle.co.uk.arcoreplayground.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import teamkunle.co.uk.arcoreplayground.R
import teamkunle.co.uk.arcoreplayground.app.ui.arcore.ARCoreActivity
import teamkunle.co.uk.arcoreplayground.base.BaseActivity

class MainActivity : BaseActivity(), MainView {

    private var presenter = MainPresenterImpl<MainView>()
    private val tag = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "MainActivity onCreate Called")
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun initView() {
        presenter.attachView(this)
        button_toast.setOnClickListener {
         presenter.setToastText("We have been clicked")
        }
    }

    override fun displayToastLaunchARcoreActivity(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ARCoreActivity::class.java)
        startActivity(intent)
    }

    override fun getLayOutResourcedId(): Int {
        return R.layout.activity_main
    }
}
