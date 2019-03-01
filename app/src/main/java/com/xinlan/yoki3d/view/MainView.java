package com.xinlan.yoki3d.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.xinlan.yoki3d.Scene;
import com.xinlan.yoki3d.primitive.Node;
import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.utils.OpenglEsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainView extends GLSurfaceView implements GLSurfaceView.Renderer, GestureDetector.OnGestureListener {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例

    private CoreRender mRender;
    private GestureDetector mGestureDetect;
    private GestureDetector.OnGestureListener mGestureListener;

    public MainView(Context context) {
        super(context);
        initView(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setGestureListener(GestureDetector.OnGestureListener listener) {
        if (mGestureListener != listener) {
            mGestureListener = listener;
        }
    }

    protected void initView(Context context) {
        mGestureDetect = new GestureDetector(context, this);

        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //getHolder().setFormat(PixelFormat.RGBA_8888);
        this.setZOrderOnTop(true);
    }

    protected Scene mCurrentScene;
    protected List<Scene> mScenes = new ArrayList<Scene>();

    private ViewCallback mCustomAction;

    public void setCustomAction(ViewCallback action) {
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

    public void removeChild(Node node) {
        if (mCurrentScene != null) {
            mCurrentScene.removeChild(node);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mRender = CoreRender.getInstance();
        CoreRender.getInstance().onCreate();

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
        if (mCustomAction != null) {
            mCustomAction.beforeOnDraw(this);
        }
        mCurrentScene.update();

        CoreRender.getInstance().render();
        if (mCustomAction != null) {
            mCustomAction.afterOnDraw(this);
        }
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
        return mGestureDetect.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        if (mGestureListener != null) {
            mGestureListener.onShowPress(e);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (mGestureListener != null) {
            return mGestureListener.onSingleTapUp(e);
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mRender != null) {
            mRender.yAngle += distanceX * TOUCH_SCALE_FACTOR;//设置沿y轴旋转角度
            mRender.xAngle += distanceY * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
        }
        if (mGestureListener != null) {
            return mGestureListener.onScroll(e1, e2, distanceX, distanceY);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (mGestureListener != null) {
            mGestureListener.onLongPress(e);
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (mGestureListener != null) {
            return mGestureListener.onFling(e1, e2, velocityX, velocityY);
        }
        return false;
    }
}//end class
