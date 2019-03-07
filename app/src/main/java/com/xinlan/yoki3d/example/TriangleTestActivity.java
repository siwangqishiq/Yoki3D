package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.opengl.GLES30;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.CustomObj;
import com.xinlan.yoki3d.primitive.Line;
import com.xinlan.yoki3d.primitive.Triangle;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class TriangleTestActivity extends Activity implements ViewCallback {
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
        mMainView.setRefreshColor(0, 0, 0, 255);

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
        Triangle t1 = new Triangle();
        t1.setNodeName("parent");
        t1.setColor(0, 0, 255, 255);
        t1.setTriangle(0, 0, 0,
                0, 1, 0,
                1, 0, 0);
        ctx.addChild(t1);

        Triangle t2 = new Triangle();
        t2.setNodeName("child");
        t2.setColor(255, 0, 0, 255);
        t2.setTriangle(0, 0, 0,
                0, 0.5f, 0,
                0.5f, 0, 0);

        Triangle t3 = new Triangle();
        t3.setNodeName("childs");
        t3.setColor(255, 255, 255, 255);
        t3.setTriangle(0, 0, 0,
                0, 0.25f, 0,
                0.25f, 0, 0);

        //t2.translate(-1, 0, 0);
        t1.addChild(t2);
        t2.addChild(t3);

        //t1.rotate(0, 0, 1, 30);
        //t2.translate(0, 0.3f, 0);
        //t2.translate(0, -0.2f, 0);
//        t1.rotate(0, 0, 1, 60);

        //t1.translate(0, 0.5f, 0);
        t2.rotate(0, 0, 1, -30);
        //t3.rotate(0, 0, 1, -30);

        //t1.rotate(0, 0, 1, -30);
        //t1.translate(0, -1, 0);
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
