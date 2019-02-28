package com.xinlan.yoki3d.render;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.utils.OpenglEsUtils;

import java.util.ArrayList;

/**
 * 核心渲染类
 * 用以维护需要渲染的对象，驱动gl完成渲染工作
 * 一个应用中 仅存在一条渲染管线
 */
public final class CoreRender {
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>();

    private static float mRefreshColorR = 0.0f;
    private static float mRefreshColorG = 0.0f;
    private static float mRefreshColorB = 0.0f;
    private static float mRefreshColorA = 0.0f;

    private int mViewWidth;
    private int mViewHeight;
    private float mRatio;

    public float yAngle;//绕Y轴旋转的角度
    public float xAngle; //绕X轴旋转的角度

    private static ThreadLocal<CoreRender> mInstances = new ThreadLocal<CoreRender>();

    //public static CoreRender render = new CoreRender();
    public static CoreRender getInstance() {
        if (mInstances.get() == null) {
            mInstances.set(new CoreRender());
        }
        return mInstances.get();
    }

    private CoreRender() {
        GLES30.glClearColor(mRefreshColorR, mRefreshColorG, mRefreshColorB, mRefreshColorA);
    }

    public void setRefreshColor(float r, float g, float b, float a) {
        mRefreshColorR = OpenglEsUtils.clamp(0.0f, 1.0f, r / 255);
        mRefreshColorG = OpenglEsUtils.clamp(0.0f, 1.0f, g / 255);
        mRefreshColorB = OpenglEsUtils.clamp(0.0f, 1.0f, b / 255);
        mRefreshColorA = OpenglEsUtils.clamp(0.0f, 1.0f, a / 255);
    }

    public void clearRenderList() {
        mRenderList.clear();
    }

    public void addRenderCmd(IRender renderCmd) {
        if (renderCmd == null)
            return;

        mRenderList.add(renderCmd);
    }

    public void onCreate(){
        GLES30.glClearColor(mRefreshColorR, mRefreshColorG, mRefreshColorB, mRefreshColorA);
        GLES30.glEnable(GLES20.GL_DEPTH_TEST);//打开深度测试
        //GLES30.glEnable(GLES30.GL_CULL_FACE);//背面裁剪
    }

    public void onViewResize(int w, int h) {
        //GLES30.glClearColor(0, 0, 0, 1f);
        mViewWidth = w;
        mViewHeight = h;

        mRatio = (float) mViewWidth / mViewHeight;

        MatrixState.getInstance().setCamera(0, 0, 0,
                0, 0, -1,
                0, 1, 0);

        GLES30.glViewport(0, 0, mViewWidth, mViewHeight);

        MatrixState.getInstance().setProjectFrustum(-mRatio, mRatio,
                -1, 1,
                1, 400);

        MatrixState.getInstance().setInitStack();
    }

    public void render() {
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        MatrixState.getInstance().pushMatrix();
        for (IRender cmd : mRenderList) {
            cmd.render();
        }//end for each
        MatrixState.getInstance().popMatrix();
    }
}//end class
