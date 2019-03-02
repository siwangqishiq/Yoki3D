package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.CustomObj;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class CustomObjActivity extends Activity implements ViewCallback {
    private MainView mMainView;
    private ToggleButton mToggleBtn;

    private CustomObj obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YokiHelper.init(getApplication());
        setContentView(R.layout.activity_customobj);
        mMainView = findViewById(R.id.mainView);
        mToggleBtn = findViewById(R.id.toggle);
        mMainView.setRefreshColor(0, 0, 0, 255);
        mMainView.setCustomAction(this);

        mToggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                obj.setLightOpen(b);
            }
        });
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
        //CustomObj obj = new CustomObj("ch_n_t.obj", R.drawable.texture3);
        obj = new CustomObj("camaro.obj", R.drawable.camaro);
        obj.setPosition(0, 0, -5);
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
