package teamkunle.co.uk.arcoreplayground.app.ui.main

import teamkunle.co.uk.arcoreplayground.base.BasePresenter


interface MainPresenter<in V : MainView> : BasePresenter<V> {
    fun setToastText(text : String)
}