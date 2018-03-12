package teamkunle.co.uk.arcoreplayground.app.ui.cameradraw.shapes

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle {

    private var vertexBuffer : FloatBuffer
    private val coords_per_vertex = 3
    private val triangleCoords = floatArrayOf( //in counterclockwise order
            0.0f, 0.622008459f, 0.0f, //top
            -0.5f, -0.311004243f, 0.0f, //bottom left
            0.5f, -0.311004243f, 0.0f ) // bottom right

    //color
    private val color = arrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    init {
        var bb : ByteBuffer = ByteBuffer.allocate(triangleCoords.size * 4)

        bb.order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()

        vertexBuffer.put(triangleCoords)

        vertexBuffer.position(0)
    }

}