package teamkunle.co.uk.arcoreplayground.utils

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ShaderUtils {

    companion object {
        fun loadShader(tag : String, context : Context, type : Int, resId : Int) : Int {

            var code : String = readRawTextFile(context, resId)
            var shader : Int = GLES20.glCreateShader(type)

            GLES20.glShaderSource(shader, code)
            GLES20.glCompileShader(shader)

            var compileStatus = IntArray(1)
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0)

            if(compileStatus[0] == 0) {
                Log.e(tag, "Error compiling shader : " + GLES20.glGetShaderInfoLog(shader))
                GLES20.glDeleteShader(shader)
                shader = 0
            }


            return shader
        }

        fun checkGLError(tag : String, label : String) {
            var lastError = GLES20.GL_NO_ERROR
            var error = GLES20.glGetError()

            if (error != GLES20.GL_NO_ERROR) {
                Log.e(tag, label + " : glError " + lastError)
            }

            if (lastError != GLES20.GL_NO_ERROR) {
                throw RuntimeException(label + " : glError " + lastError)
            }

        }

        private fun readRawTextFile(context: Context, resId: Int): String {
            var inputStream = context.resources.openRawResource(resId)

            try {
                var reader = BufferedReader(InputStreamReader(inputStream))
                var sb = StringBuilder()
                var line : String? = null

                while( { line = reader.readLine(); line }() != null) {
                    sb.append(line).append("\n")
                }

                reader.close()
                return sb.toString()

            } catch (e : IOException) {
                e.printStackTrace()
            }
            return "ran into error"
        }
    }

}
