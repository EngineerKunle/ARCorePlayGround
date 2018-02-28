package teamkunle.co.uk.arcoreplayground.base

import android.support.annotation.LayoutRes

interface BaseView {
    fun initView()
    @LayoutRes
    fun getLayOutResourcedId(): Int
}