package com.xinlan.yoki3d.primitive;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.model.ObjData;
import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.utils.LoadUtil;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * 加载自定义obj模型
 */
public class CustomObj extends RenderNode {

    protected FloatBuffer mVertexBuf;//顶点数组
    protected int mVertexCount;

    protected FloatBuffer mCoordBuf;

    protected FloatBuffer mNormalBuf;//法向量

    protected ObjData mObjData;
    protected int mTextureId;

    protected float x, y, z;

    public CustomObj(String objFilename, int textureRes) {
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.custom_obj_vertex, R.raw.custom_obj_frg);
        mUniformMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMvpMatrix");
        mUniformTextureLoc = GLES30.glGetUniformLocation(mProgramId, "uTexture");
        mUniformCameraPosLoc = GLES30.glGetUniformLocation(mProgramId, "uCameraPos");
        mUniformModelMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uModelMatrix");
        mUniformLightPosLoc = GLES30.glGetUniformLocation(mProgramId, "uLightPos");

        mObjData = LoadUtil.loadObjFromAsset(objFilename, YokiHelper.ctx.getResources());

        mVertexBuf = OpenglEsUtils.allocateBuf(mObjData.convertVertexListToArray());
        mVertexCount = mObjData.vertexList.size();

        mCoordBuf = OpenglEsUtils.allocateBuf(mObjData.convertTextureCoordListToArray());
        mTextureId = ShaderUtil.loadTexture(YokiHelper.ctx, textureRes);

        mNormalBuf = OpenglEsUtils.allocateBuf(mObjData.convertNormalListToArray());
    }

    public void setPosition(float _x, float _y, float _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public void render() {
        MatrixState.getInstance().pushMatrix();
        GLES30.glUseProgram(mProgramId);
        MatrixState.getInstance().translate(x, y, z);

        //绕Y轴、X轴旋转
        MatrixState.getInstance().rotate(CoreRender.getInstance().yAngle, 0, 1, 0);
        MatrixState.getInstance().rotate(CoreRender.getInstance().xAngle, 1, 0, 0);

        GLES30.glUniformMatrix4fv(mUniformMvpMatrixLoc, 1, false,
                MatrixState.getInstance().getFinalMatrix(), 0);

        GLES30.glUniformMatrix4fv(mUniformModelMatrixLoc, 1, false,
                MatrixState.getInstance().getMMatrix(), 0);

        GLES30.glUniform3fv(mUniformCameraPosLoc, 1, MatrixState.getInstance().getCameraPosBuf());
        GLES30.glUniform3fv(mUniformLightPosLoc, 1, MatrixState.getInstance().getPointLightPosBuf());


        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * 4, mVertexBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 2 * 4, mCoordBuf);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glVertexAttribPointer(2, 3, GLES30.GL_FLOAT, false, 3 * 4, mNormalBuf);
        GLES30.glEnableVertexAttribArray(2);

        GLES30.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureId);
        GLES30.glUniform1ui(mUniformTextureLoc, 0);

        GLES30.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
        MatrixState.getInstance().popMatrix();
    }
}//end class
