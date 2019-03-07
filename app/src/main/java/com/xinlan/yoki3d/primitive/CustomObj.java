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

    protected int mUnifromOpenLightLoc;

    protected boolean mOpenLight = true;

    protected String mObjFileName;
    protected int mTextureRes;

    public CustomObj(String objFilename, int textureRes) {
        mObjFileName = objFilename;
        mTextureRes = textureRes;

        initShader();
        initVertex();
    }

    public void setLightOpen(final boolean on) {
        if (on != mOpenLight) {
            mOpenLight = on;
        }
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

        GLES30.glUniformMatrix4fv(mUMvpMatrixLoc, 1, false,
                MatrixState.getInstance().getFinalMatrix(), 0);

        GLES30.glUniformMatrix4fv(mUModelMatrixLoc, 1, false,
                MatrixState.getInstance().getMMatrix(), 0);

        GLES30.glUniform3fv(mUCameraPosLoc, 1, MatrixState.getInstance().getCameraPosBuf());
        GLES30.glUniform3fv(mULightPosLoc, 1, MatrixState.getInstance().getPointLightPosBuf());
        GLES30.glUniform1ui(mUnifromOpenLightLoc, mOpenLight ? 1 : 0);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * 4, mVertexBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 2 * 4, mCoordBuf);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glVertexAttribPointer(2, 3, GLES30.GL_FLOAT, false, 3 * 4, mNormalBuf);
        GLES30.glEnableVertexAttribArray(2);

        GLES30.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureId);
        GLES30.glUniform1ui(mUTextureLoc, 0);

        GLES30.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
        MatrixState.getInstance().popMatrix();
    }

    @Override
    void initShader() {
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.custom_obj_vertex, R.raw.custom_obj_frg);
        mUMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMvpMatrix");
        mUTextureLoc = GLES30.glGetUniformLocation(mProgramId, "uTexture");
        mUCameraPosLoc = GLES30.glGetUniformLocation(mProgramId, "uCameraPos");
        mUModelMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uModelMatrix");
        mULightPosLoc = GLES30.glGetUniformLocation(mProgramId, "uLightPos");
        mUnifromOpenLightLoc = GLES30.glGetUniformLocation(mProgramId, "uOpenLight");
    }

    @Override
    void initVertex() {
        mObjData = LoadUtil.loadObjFromAsset(mObjFileName, YokiHelper.ctx.getResources());

        mVertexBuf = OpenglEsUtils.allocateBuf(mObjData.convertVertexListToArray());
        mVertexCount = mObjData.vertexList.size();

        mCoordBuf = OpenglEsUtils.allocateBuf(mObjData.convertTextureCoordListToArray());
        mTextureId = ShaderUtil.loadTexture(YokiHelper.ctx, mTextureRes);

        mNormalBuf = OpenglEsUtils.allocateBuf(mObjData.convertNormalListToArray());
    }
}//end class
