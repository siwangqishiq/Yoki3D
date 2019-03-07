package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.YokiHelper;
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
        t1.setColor(0 , 255 , 255 , 255);
        t1.setTriangle( 0, 0 , -1,
                0 , 1, -1 ,
                1, 0 , -1);
        ctx.addChild(t1);

    }

    @Override
    public void beforeOnDraw(MainView ctx) {

    }

    @Override
    public void afterOnDraw(MainView ctx) {

    }

    @Override
    public void onPause(MainView ctx) {

    }

    @Override
    public void onResume(MainView ctx) {

    }

}//end class
