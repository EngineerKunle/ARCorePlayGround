package teamkunle.co.uk.arcoreplayground.app.ui.main

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import teamkunle.co.uk.arcoreplayground.R
import teamkunle.co.uk.arcoreplayground.base.BaseActivity

class MainActivity : BaseActivity(), MainView {

    private var presenter = MainPresenterImpl<MainView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun displayToast(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun getLayOutResourcedId(): Int {
        return R.layout.activity_main
    }
}
