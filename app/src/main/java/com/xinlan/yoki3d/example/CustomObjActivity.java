package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.CustomObj;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class CustomObjActivity extends Activity implements ViewCallback {
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YokiHelper.init(getApplication());
        setContentView(R.layout.activity_customobj);
        mMainView = new MainView(this);
        mMainView.setRefreshColor(0, 0, 0, 0);
        //setContentView(mMainView);

        mMainView.setCustionAction(this);
        ((ViewGroup) getWindow().getDecorView()).addView(mMainView, 300, 300);
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
        CustomObj obj = new CustomObj("ch.obj");
        obj.setPosition(0, -2, -20);
        ctx.addChild(obj);
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
