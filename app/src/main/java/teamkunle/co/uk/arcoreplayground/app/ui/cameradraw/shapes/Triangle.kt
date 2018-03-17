package teamkunle.co.uk.arcoreplayground.app.ui.cameradraw.shapes

import android.opengl.GLES20
import teamkunle.co.uk.arcoreplayground.utils.ShaderUtils
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
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var program : Int = -1

    private val vertexShaderCode : String = "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}"

    private val fragmentShaderCode : String = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"

    private var positionHandle : Int = -1

    private var colorHandle : Int = -1

    private var vertexCount = triangleCoords.size / coords_per_vertex

    private var vertexStride = coords_per_vertex * 4

    init {
        var bb : ByteBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4)

        bb.order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()

        vertexBuffer.put(triangleCoords)

        vertexBuffer.position(0)

        var vertexShader : Int = ShaderUtils.loaderShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)

        var fragmentShader : Int = ShaderUtils.loaderShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = GLES20.glCreateProgram()

        GLES20.glAttachShader(program, vertexShader)

        GLES20.glAttachShader(program, fragmentShader)

        GLES20.glLinkProgram(program)
    }

    fun draw() {

        GLES20.glUseProgram(program)

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")

        GLES20.glEnableVertexAttribArray(positionHandle)

        GLES20.glVertexAttribPointer(positionHandle, coords_per_vertex,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer)

        colorHandle = GLES20.glGetUniformLocation(program, "vColor")

        GLES20.glUniform4fv(colorHandle, 1, color, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        GLES20.glDisableVertexAttribArray(positionHandle)
    }

}