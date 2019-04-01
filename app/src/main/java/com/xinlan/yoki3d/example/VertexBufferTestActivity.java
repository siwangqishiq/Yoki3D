package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.opengl.GLES30;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.DrawBuffer;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class VertexBufferTestActivity extends Activity implements ViewCallback {
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        YokiHelper.init(getApplication());
        mMainView = new MainView(this);
        setContentView(mMainView);
        mMainView.setCustomAction(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainView.onResume();
    }

    @Override
    public void init(MainView ctx) {
        DrawBuffer o1 = new DrawBuffer();
        ctx.addChild(o1);
    }

    @Override
    public void beforeOnDraw(MainView ctx) {
        GLES30.glDisable(GLES30.GL_DEPTH_TEST);
    }

    @Override
    public void afterOnDraw(MainView ctx) {
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    }

    @Override
    public void onPause(MainView ctx) {

    }

    @Override
    public void onResume(MainView ctx) {

    }

}//end class
