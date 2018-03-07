package teamkunle.co.uk.arcoreplayground.surfaceview

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent

class CustomSurfaceView(context : Context?, attr : AttributeSet?) : GLSurfaceView(context, attr) {

    private var renderer : CustomGLRenderer = CustomGLRenderer()

    init {
        setEGLContextClientVersion(2)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        preserveEGLContextOnPause = true
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}
