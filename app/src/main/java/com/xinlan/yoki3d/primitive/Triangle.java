package com.xinlan.yoki3d.primitive;

import android.opengl.GLES30;

import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Triangle extends RenderNode {
    protected float[] mPoints = new float[3 * 3];
    protected FloatBuffer mPointBuf;

    protected float[] mColor = new float[4];

    public Triangle() {
        mPointBuf = OpenglEsUtils.allocateBuf(mPoints);
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.triangle_vertex, R.raw.triangle_frg);
    }

    public void setTriangle(float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            float x3, float y3, float z3) {
        mPointBuf.position(0);
        mPointBuf.put(x1);
        mPointBuf.put(y1);
        mPointBuf.put(z1);

        mPointBuf.put(x2);
        mPointBuf.put(y2);
        mPointBuf.put(z2);

        mPointBuf.put(x3);
        mPointBuf.put(y3);
        mPointBuf.put(z3);

        mPointBuf.position(0);
    }

    public void setColor(int r, int g, int b, int a) {
        OpenglEsUtils.convertColor(r, g, b, a, mColor);
    }

    @Override
    public void render() {
        GLES30.glUseProgram(mProgramId);

    }

}//end class
