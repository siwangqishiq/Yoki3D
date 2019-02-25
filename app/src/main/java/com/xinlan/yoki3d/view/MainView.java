package com.xinlan.yoki3d.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.xinlan.yoki3d.Scene;
import com.xinlan.yoki3d.primitve.Node;
import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.utils.OpenglEsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainView extends GLSurfaceView implements GLSurfaceView.Renderer {
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
    }

    protected Scene mCurrentScene;
    protected List<Scene> mScenes = new ArrayList<Scene>();

    private IMainView mCustomAction;

    public void setCustionAction(IMainView action) {
        this.mCustomAction = action;
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

}//end class
