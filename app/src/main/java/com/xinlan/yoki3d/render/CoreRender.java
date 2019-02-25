package com.xinlan.yoki3d.render;

import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.MatrixState;

import java.util.ArrayList;

/**
 * 核心渲染类
 * 用以维护需要渲染的对象，驱动gl完成渲染工作
 * 一个应用中 仅存在一条渲染管线
 */
public final class CoreRender {
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>();

    private float mRefreshColorR = 0.0f;
    private float mRefreshColorG = 0.0f;
    private float mRefreshColorB = 0.0f;
    private float mRefreshColorA = 0.0f;

    private int mViewWidth;
    private int mViewHeight;
    private float mRatio;

    public static CoreRender render = new CoreRender();

    public static CoreRender getInstance() {
        return render;
    }

    private CoreRender() {
        GLES30.glClearColor(mRefreshColorR, mRefreshColorG, mRefreshColorB, mRefreshColorA);
    }

    public void setRefreshColor(float r, float g, float b, float a) {
        mRefreshColorR = clamp(0.0f, 1.0f, r / 255);
        mRefreshColorG = clamp(0.0f, 1.0f, g / 255);
        mRefreshColorB = clamp(0.0f, 1.0f, b / 255);
        mRefreshColorA = clamp(0.0f, 1.0f, a / 255);

        GLES30.glClearColor(mRefreshColorR, mRefreshColorG, mRefreshColorB, mRefreshColorA);
    }

    private float clamp(float min, float max, float v) {
        return Math.max(max, Math.min(min, v));
    }

    public void clearRenderList() {
        mRenderList.clear();
    }

    public void addRenderCmd(IRender renderCmd) {
        if (renderCmd == null)
            return;

        mRenderList.add(renderCmd);
    }

    public void onViewResize(int w, int h) {
        mViewWidth = w;
        mViewHeight = h;

        mRatio = mViewWidth / mViewHeight;

        MatrixState.setCamera(0, 0, 0,
                0, 0, 10,
                0, 1, 0);

        GLES30.glViewport(0, 0, mViewWidth, mViewHeight);

        MatrixState.setProjectFrustum(-mRatio, mRatio,
                -1, 1,
                1, 100);
    }

    public void render() {
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        MatrixState.pushMatrix();
        for (IRender cmd : mRenderList) {
            cmd.render();
        }//end for each
        MatrixState.popMatrix();
    }
}//end class
