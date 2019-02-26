package com.xinlan.yoki3d.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_COMPILE_STATUS;
import static android.opengl.GLES30.GL_FRAGMENT_SHADER;
import static android.opengl.GLES30.GL_LINEAR;
import static android.opengl.GLES30.GL_LINK_STATUS;
import static android.opengl.GLES30.GL_TEXTURE_2D;
import static android.opengl.GLES30.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES30.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES30.glAttachShader;
import static android.opengl.GLES30.glBindTexture;
import static android.opengl.GLES30.glCompileShader;
import static android.opengl.GLES30.glDeleteProgram;
import static android.opengl.GLES30.glDeleteShader;
import static android.opengl.GLES30.glDeleteTextures;
import static android.opengl.GLES30.glGenTextures;
import static android.opengl.GLES30.glGenerateMipmap;
import static android.opengl.GLES30.glGetProgramInfoLog;
import static android.opengl.GLES30.glGetProgramiv;
import static android.opengl.GLES30.glGetShaderInfoLog;
import static android.opengl.GLES30.glGetShaderiv;
import static android.opengl.GLES30.glLinkProgram;
import static android.opengl.GLES30.glShaderSource;
import static android.opengl.GLUtils.texImage2D;

public class OpenglEsUtils {
    private static final String TAG = "Yoki3D";

    public static long framePerSecond = 0;
    public static long lastTime = 0;

    public static void debugFps() {
        framePerSecond++;
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime >= 1000) {
            lastTime = curTime;
            Log.d(TAG, "fps = " + framePerSecond);
            //System.out.println("fps = " +framePerSecond);
            framePerSecond = 0;
        }
    }

    public static FloatBuffer allocateBuf(float array[]) {
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * Float.BYTES)
                .order(ByteOrder.nativeOrder());
        FloatBuffer buf = bb.asFloatBuffer();
        buf.put(array);
        buf.position(0);
        return buf;
    }

    public static ByteBuffer allocateBuf(byte array[]) {
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * Byte.BYTES)
                .order(ByteOrder.nativeOrder());
        bb.put(array);
        bb.position(0);
        return bb;
    }

    public static float[] convertColor(int r , int g , int b , int a){
        float[] colors = new float[4];
        colors[0] = clamp(0.0f, 1.0f, r / 255);
        colors[1] = clamp(0.0f, 1.0f, g / 255);
        colors[2] = clamp(0.0f, 1.0f, b / 255);
        colors[3] = clamp(0.0f, 1.0f, a / 255);
        return colors;
    }

    public static float clamp(float min, float max, float v) {
        if(v <= min)
            return min;
        if(v >= max)
            return max;
        return v;
    }

}//end class
