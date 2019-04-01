package com.xinlan.yoki3d.primitive;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.FloatBuffer;
import java.nio.file.FileAlreadyExistsException;

public class DrawBuffer extends RenderNode {
    public float[] mVertex = {
            0.5f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.5f, 0.0f, 0.0f
    };

    private int bufferId;
    private FloatBuffer mVertexBuf;

    public DrawBuffer(){
        initShader();
        initVertex();
    }

    @Override
    void initShader() {
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.triangle_buffer_vertex, R.raw.triangle_buffer_frg);
        mUMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMvpMatrix");
    }

    @Override
    void initVertex() {
        int[] ids = new int[1];
        mVertexBuf = OpenglEsUtils.allocateBuf(mVertex);

        GLES30.glGenBuffers(1, ids, 0);
        bufferId = ids[0];
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufferId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,
                mVertex.length * Float.BYTES, mVertexBuf, GLES30.GL_STATIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void render() {
        MatrixState.getInstance().pushMatrix();

        GLES30.glUseProgram(mProgramId);

        GLES30.glUniformMatrix4fv(mUMvpMatrixLoc, 1, false,
                MatrixState.getInstance().getFinalMatrix(), 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufferId);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, 0);
        //GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        MatrixState.getInstance().popMatrix();
    }

}//end class
