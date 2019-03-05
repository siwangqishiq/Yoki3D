package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.Sphere;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class SphereActivity extends Activity implements ViewCallback {
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YokiHelper.init(getApplication());
        mMainView = new MainView(this);
        setContentView(mMainView);
        mMainView.setRefreshColor(0, 0, 0, 255);
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
        MatrixState.getInstance().setPointLightPos(100,100,0);

        Sphere planet = new Sphere(2);
        planet.setPosition(0,0,-10);
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
