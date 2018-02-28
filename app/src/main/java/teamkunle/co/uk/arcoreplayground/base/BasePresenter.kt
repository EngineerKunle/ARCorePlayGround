package teamkunle.co.uk.arcoreplayground.base


interface BasePresenter<in V : BaseView> {

    fun attachView(view : V)
    fun detachView()
}