package com.xinlan.yoki3d.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xinlan.yoki3d.Scene;
import com.xinlan.yoki3d.primitive.Node;
import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.utils.OpenglEsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例

    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标

    private CoreRender mRender;

    public MainView(Context context) {
        super(context);
        initView(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    protected void initView(Context context) {
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    protected Scene mCurrentScene;
    protected List<Scene> mScenes = new ArrayList<Scene>();

    private ViewCallback mCustomAction;

    public void setCustionAction(ViewCallback action) {
        this.mCustomAction = action;
        startRender();
    }

    public void startRender() {
        setRenderer(this);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    public void setRefreshColor(int r, int g, int b, int a) {
        CoreRender.getInstance().setRefreshColor(r, g, b, a);
    }

    public void addChild(Node node) {
        if (mCurrentScene != null) {
            mCurrentScene.addChild(node);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mRender = CoreRender.getInstance();
        mCurrentScene = new Scene();

        if (mCustomAction != null) {
            mCustomAction.init(this);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        CoreRender.getInstance().onViewResize(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mCurrentScene.update();

        CoreRender.getInstance().render();
        OpenglEsUtils.debugFps();
    }

    @Override
    public void onPause() {
        if (mCustomAction != null) {
            super.onPause();
            mCustomAction.onPause(this);
        }
    }

    @Override
    public void onResume() {
        if (mCustomAction != null) {
            super.onResume();
            mCustomAction.onResume(this);
        }
    }

    public Scene getCurrentScene() {
        return mCurrentScene;
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                if (mRender != null) {
                    mRender.yAngle += dx * TOUCH_SCALE_FACTOR;//设置沿y轴旋转角度
                    mRender.xAngle += dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
                }
                //requestRender();//重绘画面
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

}//end class
