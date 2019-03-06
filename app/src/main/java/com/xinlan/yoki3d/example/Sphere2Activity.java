package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.Sphere;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class Sphere2Activity extends Activity implements ViewCallback {
    private MainView mMainView;

    private Sphere planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YokiHelper.init(getApplication());

        mMainView = new MainView(this);
        setContentView(mMainView);

        mMainView.setRefreshColor(255, 255, 255, 255);
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
        MatrixState.getInstance().setPointLightPos(0, 200, 0);

        planet = new Sphere(4);
        planet.setPosition(0, 0, -10);
        ctx.addChild(planet);
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
