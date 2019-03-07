package com.xinlan.yoki3d.primitive;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.FloatBuffer;

public class Triangle extends RenderNode {
    protected float[] mPoints = new float[3 * 3];
    protected FloatBuffer mPointBuf;

    protected int mUColorLoc;
    protected float[] mColor = new float[4];

    public Triangle() {
        init();
    }

    private void init() {
        initShader();
        initVertex();
    }

    public void setTriangle(float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            float x3, float y3, float z3) {
        mPoints[0] = x1;
        mPoints[1] = y1;
        mPoints[2] = z1;

        mPoints[3] = x2;
        mPoints[4] = y2;
        mPoints[5] = z2;

        mPoints[6] = x3;
        mPoints[7] = y3;
        mPoints[8] = z3;


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
    void initVertex() {
        mPointBuf = OpenglEsUtils.allocateBuf(mPoints);
    }

    @Override
    void initShader() {
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.triangle_vertex, R.raw.triangle_frg);
        mUMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMvpMatrix");
        mUColorLoc = GLES30.glGetUniformLocation(mProgramId, "uColor");
    }

    @Override
    public void render() {
        MatrixState.getInstance().pushMatrix();

        //绕Y轴、X轴旋转
        //MatrixState.getInstance().rotate(CoreRender.getInstance().yAngle, mPoints[0], mPoints[1], mPoints[2]);
        //MatrixState.getInstance().rotate(CoreRender.getInstance().xAngle, mPoints[3], mPoints[4], mPoints[5]);

        GLES30.glUseProgram(mProgramId);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * 4, mPointBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glUniform4fv(mUColorLoc, 1, mColor, 0);

        GLES30.glUniformMatrix4fv(mUMvpMatrixLoc, 1, false,
                MatrixState.getInstance().getFinalMatrix(), 0);

        GLES30.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        MatrixState.getInstance().popMatrix();
    }

}//end class
