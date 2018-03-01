package teamkunle.co.uk.arcoreplayground.app.ui.arcore

import teamkunle.co.uk.arcoreplayground.base.BasePresenter


class ARCorePresenterImpl<in V : ARCoreView> : BasePresenter<V>{

    private var view : V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}