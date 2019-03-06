package com.xinlan.yoki3d.primitive;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.model.ObjData;
import com.xinlan.yoki3d.model.Vec3;
import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.utils.LoadUtil;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * 球形
 */
public class Sphere extends RenderNode {

    protected float mRadius;
    protected Vec3 mCenter = new Vec3(0, 0, 0);
    protected FloatBuffer mVertexBuf;
    protected int mVertexCount;

    protected int mUAmbientLightLoc;
    protected int mUDiffuseLightLoc;
    protected int mUSpecularLightLoc;

    protected float mAmbient = 0.4f;
    protected float mDiffuse = 0.85f;
    protected float mSpecular = 0.2f;

    public Sphere(float radius) {
        this.mRadius = radius;
        initShader();
        initVertex();
    }

    public void setPosition(float x, float y, float z) {
        mCenter.x = x;
        mCenter.y = y;
        mCenter.z = z;
    }

    public void setLightStrength(float ambient, float diffuse, float specular) {
        mAmbient = ambient;
        mDiffuse = diffuse;
        mSpecular = specular;
    }

    public void setAmbientLightStrength(float ambient) {
        mAmbient = ambient;
    }

    public void setDiffuseLightStrength(float diffuse) {
        mDiffuse = diffuse;
    }

    public void setSpecularLightStrength(float specular) {
        mSpecular = specular;
    }

    protected void initShader() {
        mProgramId = ShaderUtil.buildShaderProgram(R.raw.sphere_vertex, R.raw.sphere_frg);
        mUniformMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMvpMatrix");
        mUniformCameraPosLoc = GLES30.glGetUniformLocation(mProgramId, "uCameraPos");
        mUniformModelMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uModelMatrix");
        mUniformLightPosLoc = GLES30.glGetUniformLocation(mProgramId, "uLightPos");

        mUAmbientLightLoc = GLES30.glGetUniformLocation(mProgramId, "uAmbientLight");
        mUDiffuseLightLoc = GLES30.glGetUniformLocation(mProgramId, "uDiffuseLight");
        mUSpecularLightLoc = GLES30.glGetUniformLocation(mProgramId, "uSepcularLight");
    }

    protected void initVertex() {
        ArrayList<Float> alVertix = new ArrayList<Float>();//存放顶点坐标的ArrayList
        final float angleSpan = 10f;//将球进行单位切分的角度
        for (float vAngle = 90; vAngle > -90; vAngle = vAngle - angleSpan) {//垂直方向angleSpan度一份
            for (float hAngle = 360; hAngle > 0; hAngle = hAngle - angleSpan) {//水平方向angleSpan度一份
                //纵向横向各到一个角度后计算对应的此点在球面上的坐标
                double xozLength = mRadius * Math.cos(Math.toRadians(vAngle));
                float x1 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                float z1 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                float y1 = (float) (mRadius * Math.sin(Math.toRadians(vAngle)));
                xozLength = mRadius * Math.cos(Math.toRadians(vAngle - angleSpan));
                float x2 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                float z2 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                float y2 = (float) (mRadius * Math.sin(Math.toRadians(vAngle - angleSpan)));
                xozLength = mRadius * Math.cos(Math.toRadians(vAngle - angleSpan));
                float x3 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                float z3 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                float y3 = (float) (mRadius * Math.sin(Math.toRadians(vAngle - angleSpan)));
                xozLength = mRadius * Math.cos(Math.toRadians(vAngle));
                float x4 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                float z4 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                float y4 = (float) (mRadius * Math.sin(Math.toRadians(vAngle)));
                //构建第一三角形
                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x4);
                alVertix.add(y4);
                alVertix.add(z4);
                //构建第二三角形
                alVertix.add(x4);
                alVertix.add(y4);
                alVertix.add(z4);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
            }
        }//end for

        mVertexCount = alVertix.size() / 3;
        float[] vArray = new float[alVertix.size()];
        for (int i = 0; i < alVertix.size(); i++) {
            vArray[i] = alVertix.get(i);
        }
        mVertexBuf = OpenglEsUtils.allocateBuf(vArray);
    }

    public void render() {
        MatrixState.getInstance().pushMatrix();
        GLES30.glUseProgram(mProgramId);
        MatrixState.getInstance().translate(mCenter.x, mCenter.y, mCenter.z);

        //绕Y轴、X轴旋转
        MatrixState.getInstance().rotate(CoreRender.getInstance().yAngle,
                0, 1, 0);

        GLES30.glUniformMatrix4fv(mUniformMvpMatrixLoc, 1, false,
                MatrixState.getInstance().getFinalMatrix(), 0);
        GLES30.glUniformMatrix4fv(mUniformModelMatrixLoc, 1, false,
                MatrixState.getInstance().getMMatrix(), 0);
        GLES30.glUniform3fv(mUniformCameraPosLoc, 1, MatrixState.getInstance().getCameraPosBuf());
        GLES30.glUniform3fv(mUniformLightPosLoc, 1, MatrixState.getInstance().getPointLightPosBuf());

        GLES30.glUniform1f(mUAmbientLightLoc, mAmbient);
        GLES30.glUniform1f(mUDiffuseLightLoc, mDiffuse);
        GLES30.glUniform1f(mUSpecularLightLoc, mSpecular);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * 4, mVertexBuf);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 3 * 4, mVertexBuf);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
        MatrixState.getInstance().popMatrix();
    }
}//end class
