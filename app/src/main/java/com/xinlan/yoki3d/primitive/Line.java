package com.xinlan.yoki3d.primitive;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Line extends RenderNode {
    protected float[] mPoints = new float[2 * 3];
    protected FloatBuffer mPointBuf;

    protected float[] mColor = new float[4];
    protected int mUnifromColorLoc;

    protected float mLineWidth = 1;

    public Line() {
        mPointBuf = OpenglEsUtils.allocateBuf(mPoints);
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.line_vertex, R.raw.line_frg);

        mUnifromColorLoc = GLES30.glGetUniformLocation(mProgramId, "uColor");
        mUniformMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMvpMatrix");
    }

    public void setPoints(float startX, float startY, float startZ,
                          float endX, float endY, float endZ) {
        mPointBuf.position(0);

        mPointBuf.put(startX);
        mPointBuf.put(startY);
        mPointBuf.put(startZ);

        mPointBuf.put(endX);
        mPointBuf.put(endY);
        mPointBuf.put(endZ);

        mPointBuf.position(0);
    }

    public void setColor(int r, int g, int b, int a) {
        OpenglEsUtils.convertColor(r, g, b, a, mColor);
    }

    @Override
    public void render() {

        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix4fv(mUniformMvpMatrixLoc, 1, false,
                MatrixState.getInstance().getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * 4, mPointBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glUniform4fv(mUnifromColorLoc, 1, mColor, 0);
        GLES30.glLineWidth(mLineWidth);
        GLES30.glDrawArrays(GLES20.GL_LINES, 0, 2);
        GLES30.glLineWidth(1.0f);
    }
}//end class
