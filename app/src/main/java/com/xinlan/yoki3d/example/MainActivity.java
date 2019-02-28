package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.Point;
import com.xinlan.yoki3d.view.ViewCallback;
import com.xinlan.yoki3d.view.MainView;

public class MainActivity extends Activity implements ViewCallback {
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

        for (float i = 0; i < 2; i += 0.01f) {
            Point p1 = new Point();
            p1.setColor(255, 255, 0);
            p1.setSize(100*i + 1);
            p1.setPosition(i, i, 0);
            ctx.addChild(p1);
        }//end for i

        Point p2 = new Point();
        p2.setColor(255, 255, 255);
        p2.setSize(10);
        p2.setPosition(0, 1, 0);
        ctx.addChild(p2);

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
