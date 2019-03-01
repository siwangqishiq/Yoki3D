package com.xinlan.yoki3d.primitive;

import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Triangle extends RenderNode {
    protected float[] mPoints = new float[2 * 3];
    protected FloatBuffer mPointBuf;

    protected float[] mColor = new float[4];
    protected ByteBuffer mColorBuf;

    protected float mLineWidth;

    public Triangle() {
        mPointBuf = OpenglEsUtils.allocateBuf(mPoints);
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.line_vertex, R.raw.line_frg);
    }

    public void setLine(float x1, float y1, float z1,
                        float x2, float y2, float z2) {

    }

    public void setColor(int r, int g, int b, int a) {

    }

    @Override
    public void render() {

    }
}//end class
