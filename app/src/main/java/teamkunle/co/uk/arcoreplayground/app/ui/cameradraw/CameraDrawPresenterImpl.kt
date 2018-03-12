package teamkunle.co.uk.arcoreplayground.app.ui.cameradraw

import teamkunle.co.uk.arcoreplayground.base.BasePresenter


class CameraDrawPresenterImpl<in V : CameraDrawView> : BasePresenter<V>{

    private var view : V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}